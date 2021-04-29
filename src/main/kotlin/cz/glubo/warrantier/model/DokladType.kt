package cz.glubo.warrantier.model

enum class DokladType(val upstreamIdentifier: Int) {
    /** Obcansky prukaz */
    OP(0),
    /** Obcansky prukaz obsahujici serii */
    OPS( 1),
    /** Cestovni pas centralne vydavany (fialovy) */
    CD( 2),
    /** Zbrojni prukaz */
    ZP(4),
}
