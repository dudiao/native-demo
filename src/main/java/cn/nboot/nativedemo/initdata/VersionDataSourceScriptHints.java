package cn.nboot.nativedemo.initdata;

import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.type.NativeConfiguration;

@NativeHint(trigger = VersionDataSourceScriptDatabaseInitializer.class,
    resources = @ResourceHint(patterns = {"cn/nboot/nativedemo/initdata/data/*.sql", "cn/nboot/nativedemo/initdata/schema/*.sql"}))
public class VersionDataSourceScriptHints implements NativeConfiguration {

}
