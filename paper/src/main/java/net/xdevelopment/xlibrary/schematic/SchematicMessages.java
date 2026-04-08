package net.xdevelopment.xlibrary.schematic;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SchematicMessages {

    public final String PREFIX = "<dark_gray>[<gradient:#6C5CE7:#A29BFE>Schematic</gradient><dark_gray>] ";

    public final String POS1_SET = PREFIX + "<white>Точка <gray>1</gray> установлена на <gray><x></gray>, <gray><y></gray>, <gray><z></gray>.";
    public final String POS2_SET = PREFIX + "<white>Точка <gray>2</gray> установлена на <gray><x></gray>, <gray><y></gray>, <gray><z></gray>.";

    public final String SAVE_START = PREFIX + "<white>Сохранение схематики <gray><name></gray>...";
    public final String SAVE_SUCCESS = PREFIX + "<white>Схематика <gray><name></gray> успешно сохранена.";
    public final String SAVE_FAIL = PREFIX + "<red>Ошибка при сохранении схематики <gray><name></gray>.";

    public final String PASTE_START = PREFIX + "<white>Вставка схематики <gray><name></gray>...";
    public final String PASTE_SUCCESS = PREFIX + "<white>Схематика <gray><name></gray> успешно вставлена.";
    public final String PASTE_FAIL = PREFIX + "<red>Ошибка при вставке схематики <gray><name></gray>.";

    public final String UNDO_SUCCESS = PREFIX + "<white>Последняя операция <gray>отменена</gray>.";
    public final String UNDO_FAIL = PREFIX + "<red>Нет операции для отмены.";

    public final String NO_SELECTION = PREFIX + "<red>Выделите регион: установите <gray>точку 1</gray> и <gray>точку 2</gray>.";
    public final String NO_FILE = PREFIX + "<red>Файл схематики <gray><name></gray> не найден.";
    public final String NO_PERMISSION = PREFIX + "<red>У вас нет прав для этой команды.";

    public final String WAND_GIVE = PREFIX + "<white>Вы получили <gray>инструмент выделения</gray>.";
    public final String WAND_LEFT = PREFIX + "<white>ЛКМ — <gray>точка 1</gray>, ПКМ — <gray>точка 2</gray>.";

    public final String CONVERT_START = PREFIX + "<white>Конвертация <gray>.esrc</gray> → <gray>.json</gray>...";
    public final String CONVERT_SUCCESS = PREFIX + "<white>Конвертировано <gray><count></gray> схематик.";
    public final String CONVERT_EMPTY = PREFIX + "<yellow>Нет <gray>.esrc</gray> файлов для конвертации.";
    public final String CONVERT_FAIL = PREFIX + "<red>Ошибка при конвертации.";

    public final String HELP_HEADER = PREFIX + "<white>Доступные команды:";
    public final String HELP_WAND = "  <gray>/xlibrary schematic wand</gray> <dark_gray>—</dark_gray> <white>получить инструмент выделения";
    public final String HELP_SAVE = "  <gray>/xlibrary schematic save <name></gray> <dark_gray>—</dark_gray> <white>сохранить регион";
    public final String HELP_PASTE = "  <gray>/xlibrary schematic paste <name></gray> <dark_gray>—</dark_gray> <white>вставить схематику";
    public final String HELP_UNDO = "  <gray>/xlibrary schematic undo</gray> <dark_gray>—</dark_gray> <white>отменить последнюю вставку";
    public final String HELP_CONVERT = "  <gray>/xlibrary convert schematic</gray> <dark_gray>—</dark_gray> <white>конвертировать .esrc → .json";
}
