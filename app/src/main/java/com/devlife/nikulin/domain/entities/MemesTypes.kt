package com.devlife.nikulin.domain.entities

enum class MemesTypes(val titleName: String, val remoteName: String) {
        LATEST("Последние", "latest"),
        TOP("Лучшие", "top"),
        HOT("Горячие", "hot");

        companion object {
            fun getTabsList(): List<MemesTypes> {
                return listOf(LATEST, TOP, HOT)
            }
        }

        fun getName(value: MemesTypes): String {
            return when(value) {
                LATEST -> "Последние"
                TOP -> "Лучшие"
                HOT -> "Горячие"
            }
        }

        fun getRemoteName(type: MemesTypes): String {
            return when(type) {
                LATEST -> "latest"
                TOP -> "top"
                HOT -> "hot"
            }
        }
}
