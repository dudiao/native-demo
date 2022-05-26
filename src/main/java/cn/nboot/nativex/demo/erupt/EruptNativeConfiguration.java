package cn.nboot.nativex.demo.erupt;

import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.type.NativeConfiguration;
import xyz.erupt.security.interceptor.EruptSecurityInterceptor;
import xyz.erupt.tpl.engine.BeetlEngine;
import xyz.erupt.tpl.engine.EnjoyEngine;
import xyz.erupt.tpl.engine.FreemarkerEngine;
import xyz.erupt.tpl.engine.NativeEngine;
import xyz.erupt.tpl.engine.ThymeleafEngine;
import xyz.erupt.tpl.engine.VelocityTplEngine;

/**
 * @author songyinyin
 * @since 2022/5/15 00:00
 */
@NativeHint(types = @TypeHint(types = {
    BeetlEngine.class,
    EnjoyEngine.class,
    FreemarkerEngine.class,
    NativeEngine.class,
    ThymeleafEngine.class,
    VelocityTplEngine.class,
    EruptSecurityInterceptor.class}),
    resources = @ResourceHint(patterns = {"ip2region.db", "^tpl/.*"})
    )
public class EruptNativeConfiguration implements NativeConfiguration {
}
