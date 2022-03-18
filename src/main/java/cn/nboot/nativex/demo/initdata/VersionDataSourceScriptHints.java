package cn.nboot.nativex.demo.initdata;

import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.type.NativeConfiguration;

/**
 * @author songyinyin
 * @since 2022/3/19 00:51
 */
@NativeHint(trigger = DataSourceScriptDatabaseRunner.class,
    resources = @ResourceHint(patterns = {"db/migration/*.sql"}))
public class VersionDataSourceScriptHints implements NativeConfiguration {

}
