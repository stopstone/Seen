package com.dev_stopstone.seen

object Storage {
    fun getDummyData(): List<LostItem> {
        return listOf(
            LostItem(
                "0",
                "마르지엘라 지갑",
                "https://github.com/stopstone/kotlin-menu/assets/77120604/525306c8-9fcd-4ed9-ba49-59f4e4504c15",
                "갤러리아 백화점 근처에서 잃어버렸습니다.\n" +
                        "선물로 받은 거라 꼭 찾고 싶어요 ㅠㅠ\n" +
                        "사례 꼭 하겠습니다!!",
                "천안시 동남구",
                "2024년 4월 14일",
                "2024-03-02",
                50000
            ),
        )
    }

}