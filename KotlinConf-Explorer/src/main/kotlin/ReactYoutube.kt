@file:JsModule("react-player")
@file:JsNonModule

import react.*

@JsName("default")
external val ReactPlayer: ComponentClass<dynamic>

external interface ReactPlayerProps: Props {
    var url: String
    var controls: Boolean
}