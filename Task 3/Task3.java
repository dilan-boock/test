import java.io.*;
import com.google.gson.*;

public class Task3 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ReportBuilder <tests_file_path> <values_file_path>");
            return;
        }

        String testsFilePath = args[0];
        String valuesFilePath = args[1];

        try {
            // Чтение файла tests.json
            JsonElement testsJson = readJsonFile(testsFilePath);

            // Чтение файла values.json
            JsonElement valuesJson = readJsonFile(valuesFilePath);

            // Формирование отчета на основании значений
            JsonElement reportJson = buildReport(testsJson, valuesJson);

            // Запись отчета в файл report.json
            writeJsonToFile(reportJson, "report.json");

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonElement readJsonFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(reader);
    }

    private static void writeJsonToFile(JsonElement jsonElement, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(jsonElement);
        writer.write(jsonString);
        writer.close();
    }

    private static JsonElement buildReport(JsonElement testsJson, JsonElement valuesJson) {
        if (testsJson.isJsonObject()) {
            JsonObject testsObject = testsJson.getAsJsonObject();
            JsonObject reportObject = new JsonObject();

            if (testsObject.has("id")) {
                reportObject.addProperty("id", testsObject.get("id").getAsInt());
            }

            if (testsObject.has("title")) {
                reportObject.addProperty("title", testsObject.get("title").getAsString());
            }

            if (testsObject.has("value")) {
                // Заполнение поля "value" из values.json
                if (valuesJson.isJsonArray()) {
                    JsonArray valuesArray = valuesJson.getAsJsonArray();
                    for (JsonElement element : valuesArray) {
                        if (element.isJsonObject()) {
                            JsonObject valueObject = element.getAsJsonObject();
                            if (valueObject.has("id") && valueObject.get("id").getAsInt() == reportObject.get("id").getAsInt()) {
                                reportObject.addProperty("value", valueObject.get("value").getAsString());
                                break;
                            }
                        }
                    }
                }
            }

            if (testsObject.has("values")) {
                // Рекурсивно обрабатываем вложенные структуры
                JsonArray valuesArray = testsObject.getAsJsonArray("values");
                JsonArray reportValuesArray = new JsonArray();

                for (JsonElement element : valuesArray) {
                    if (element.isJsonObject()) {
                        JsonObject valueObject = element.getAsJsonObject();
                        JsonObject reportValueObject = new JsonObject();

                        if (valueObject.has("id")) {
                            reportValueObject.addProperty("id", valueObject.get("id").getAsInt());
                        }

                        if (valueObject.has("title")) {
                            reportValueObject.addProperty("title", valueObject.get("title").getAsString());
                        }

                        if (valueObject.has("value")) {
                            // Заполнение поля "value" из values.json
                            if (valuesJson.isJsonArray()) {
                                JsonArray valuesArray2 = valuesJson.getAsJsonArray();
                                for (JsonElement element2 : valuesArray2) {
                                    if (element2.isJsonObject()) {
                                        JsonObject valueObject2 = element2.getAsJsonObject();
                                        if (valueObject2.has("id") && valueObject2.get("id").getAsInt() == reportValueObject.get("id").getAsInt()) {
                                            reportValueObject.addProperty("value", valueObject2.get("value").getAsString());
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        reportValuesArray.add(reportValueObject);
                    }
                }

                reportObject.add("values", reportValuesArray);
            }

            return reportObject;
        }

        return JsonNull.INSTANCE;
    }
}