package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppData;
import seedu.address.testutil.TypicalAppData;

class JsonSerializableAppDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAppDataTest");
    private static final Path TYPICAL_APPDATA_FILE = TEST_DATA_FOLDER.resolve("typicalAppData.json");
    private static final Path INVALID_APPDATA_FILE = TEST_DATA_FOLDER.resolve("invalidAppData.json");
    private static final Path DUPLICATE_APPDATA_FILE = TEST_DATA_FOLDER.resolve("duplicateAppData.json");
    private static final Path DUPLICATE_TASKDATA_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskData.json");

    @Test
    void toModelType_typicalAppDataFile_success() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        AppData appDataFromFile = dataFromFile.toModelType();
        AppData typicalAppData = TypicalAppData.getTypicalAppData();
        assertEquals(appDataFromFile, typicalAppData);
    }

    @Test
    void toModelType_invalidAppDataFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(INVALID_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    void toModelType_duplicateAppData_throwsIllegalValueException() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAppData.MESSAGE_DUPLICATE_TITLE,
                dataFromFile::toModelType);
    }

    @Test
    void toModelType_duplicateTaskData_throwsIllegalValueException() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASKDATA_FILE,
                JsonSerializableAppData.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAppData.MESSAGE_DUPLICATE_TASK,
                dataFromFile::toModelType);
    }

}
