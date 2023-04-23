package ua.blackwind.limbushelper.domain.sinner.model

import ua.blackwind.limbushelper.domain.common.DamageType
import ua.blackwind.limbushelper.domain.common.Effect
import ua.blackwind.limbushelper.domain.common.Sin

data class Skill(
    val id: Int,
    val name: String,
    val dmgType: DamageType,
    val sin: Sin,
    val copiesCount: Int,
    val basePower: Int,
    val coinPower: Int,
    val coinCount: Int,
    val effects: List<Effect>
)
