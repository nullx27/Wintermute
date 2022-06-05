package tech.grimm.wintermute.services.models



class Assumptions {
    var type: String? = null
    var template: String? = null
    var count = 0
    var values: ArrayList<Value>? = null
}

class Expressiontypes {
    var name: String? = null
}

class Img {
    var src: String? = null
    var alt: String? = null
    var title: String? = null
    var width = 0
    var height = 0
    var type: String? = null
    var themes: String? = null
    var colorinvertable = false
    var contenttype: String? = null
}

class Pod {
    var title: String? = null
    var scanner: String? = null
    var id: String? = null
    var position = 0
    var error = false
    var numsubpods = 0
    var subpods: ArrayList<Subpod>? = null
    var expressiontypes: Array<Expressiontypes>? = null
    var primary = false
    var states: ArrayList<State>? = null
}

class Queryresult {
    var success = false
    var error = false
    var numpods = 0
    var datatypes: String? = null
    var timedout: String? = null
    var timedoutpods: String? = null
    var timing = 0.0
    var parsetiming = 0.0
    var parsetimedout = false
    var recalculate: String? = null
    var id: String? = null
    var host: String? = null
    var server: String? = null
    var related: String? = null
    var version: String? = null
    var inputstring: String? = null
    var pods: ArrayList<Pod>? = null
    var assumptions: Assumptions? = null
}

class WolframAlphaResponse {
    var queryresult: Queryresult? = null
}

class State {
    var name: String? = null
    var input: String? = null
    var stepbystep = false
}

class Subpod {
    var title: String? = null
    var img: Img? = null
    var plaintext: String? = null
}

class Value {
    var name: String? = null
    var desc: String? = null
    var input: String? = null
}