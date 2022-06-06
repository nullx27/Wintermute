package tech.grimm.wintermute.services.models

data class Assumptions(
    var type: String,
    var template: String,
    var count: Int,
    var values: ArrayList<Value>
)

data class Img(
    var src: String,
    var alt: String,
    var title: String,
    var width: Int,
    var height: Int,
    var type: String,
    var themes: String,
    var colorinvertable: Boolean,
    var contenttype: String,
)

data class Pod(
    var title: String,
    var scanner: String,
    var id: String,
    var position: Int,
    var error: Boolean,
    var numsubpods: Int,
    var subpods: ArrayList<Subpod>,
    var expressiontypes: Any, //this is a workaround b/c wolframalpha api returns inconsistent types here
    var primary: Boolean,
    var states: ArrayList<State>,
)

data class Queryresult(
    var success: Boolean,
    var error: Boolean,
    var numpods: Int,
    var datatypes: String,
    var timedout: String,
    var timedoutpods: String,
    var timing: Float,
    var parsetiming: Float,
    var parsetimedout: Boolean,
    var recalculate: String,
    var id: String,
    var host: String,
    var server: String,
    var related: String,
    var version: String,
    var inputstring: String,
    var pods: ArrayList<Pod>,
    var assumptions: Assumptions,
)

data class WolframAlphaResponse(
    var queryresult: Queryresult,
)

data class State(
    var name: String,
    var input: String,
    var stepbystep: Boolean,
)

class Subpod(
    var title: String,
    var img: Img,
    var plaintext: String,
)

data class Value(
    var name: String,
    var desc: String,
    var input: String,
)