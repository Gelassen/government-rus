package ru.home.government.stubs

import com.google.gson.Gson
import ru.home.government.model.GovResponse
import ru.home.government.model.VotesResponse
import java.util.*

object Stubs {

    object ApiResponse {

        const val lawsOkV2ApiRes =
            "{\n" +
                    "  \"count\": 30795,\n" +
                    "  \"page\": 2,\n" +
                    "  \"wording\": \"Законопроекты, отсортированные по дате последнего события (по убыванию)\",\n" +
                    "  \"laws\": [\n" +
                    "    {\n" +
                    "      \"id\": 33736,\n" +
                    "      \"number\": \"10309-8\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"Об охоте и о сохранении охотничьих ресурсов и о внесении изменений в отдельные законодательные акты Российской Федерации\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/10309-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-11-01\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12534800,\n" +
                    "            \"name\": \"Комитет ГД по экологии, природным ресурсам и охране окружающей среды\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33730,\n" +
                    "      \"number\": \"9734-8\",\n" +
                    "      \"name\": \"О внесении изменений в некоторые законодательные акты Российской Федерации\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9734-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-11-01\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99112983\",\n" +
                    "            \"name\": \"Василенко Дмитрий Юрьевич\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112472\",\n" +
                    "            \"name\": \"Гумерова Лилия Салаватовна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99107923\",\n" +
                    "            \"name\": \"Умаханов Ильяс Магомед-Саламович\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12535000,\n" +
                    "            \"name\": \"Комитет ГД по науке и высшему образованию\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    }" +
                    "]\n" +
    "}"

        const val incompleteLawV2ApiRes = "{\n" +
                "   \"count\":1,\n" +
                "   \"page\":1,\n" +
                "   \"wording\":\"Законопроекты, где номер \\\"16493-8\\\", отсортированные по дате последнего события (по убыванию)\",\n" +
                "   \"laws\":[\n" +
                "      {\n" +
                "         \"id\":33758,\n" +
                "         \"number\":\"16493-8\",\n" +
                "         \"name\":\"О внесении изменений в Федеральный закон \\\"Об охране озера Байкал\\\"\",\n" +
                "         \"comments\":null,\n" +
                "         \"introductionDate\":\"2021-11-11\",\n" +
                "         \"url\":\"http:\\/\\/sozd.parlament.gov.ru\\/bill\\/16493-8\",\n" +
                "         \"transcriptUrl\":null,\n" +
                "         \"lastEvent\":{\n" +
                "            \n" +
                "         },\n" +
                "         \"subject\":{\n" +
                "            \"deputies\":[\n" +
                "               {\n" +
                "                  \"id\":\"99112913\",\n" +
                "                  \"name\":\"Будуев Николай Робертович\",\n" +
                "                  \"position\":\"Депутат ГД\",\n" +
                "                  \"isCurrent\":false\n" +
                "               },\n" +
                "               {\n" +
                "                  \"id\":\"99111804\",\n" +
                "                  \"name\":\"Валуев Николай Сергеевич\",\n" +
                "                  \"position\":\"Депутат ГД\",\n" +
                "                  \"isCurrent\":false\n" +
                "               },\n" +
                "               {\n" +
                "                  \"id\":\"99112007\",\n" +
                "                  \"name\":\"Тен Сергей Юрьевич\",\n" +
                "                  \"position\":\"Депутат ГД\",\n" +
                "                  \"isCurrent\":false\n" +
                "               }\n" +
                "            ],\n" +
                "            \"departments\":[\n" +
                "               \n" +
                "            ],\n" +
                "            \"factions\":[\n" +
                "               {\n" +
                "                  \"id\":\"72100024\",\n" +
                "                  \"name\":\"Фракция Всероссийской политической партии \\\"ЕДИНАЯ РОССИЯ\\\"\"\n" +
                "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         \"committees\":{\n" +
                "            \"responsible\":null,\n" +
                "            \"profile\":[\n" +
                "               \n" +
                "            ],\n" +
                "            \"soexecutor\":[\n" +
                "               \n" +
                "            ]\n" +
                "         },\n" +
                "         \"type\":{\n" +
                "            \"id\":38,\n" +
                "            \"name\":\"Федеральный закон\"\n" +
                "         }\n" +
                "      }\n" +
                "   ]\n" +
                "}"

        const val votesResponse = "{\n" +
                "   \"totalCount\":\"2\",\n" +
                "   \"page\":\"1\",\n" +
                "   \"pageSize\":\"20\",\n" +
                "   \"wording\":\"Результаты голосования по законопроекту \\\"301854-7\\\", вынесенному для открытого голосования за 01.01.1970.\",\n" +
                "   \"votes\":[\n" +
                "      {\n" +
                "         \"id\":113387,\n" +
                "         \"subject\":\"(к отклонению) О проекте федерального закона \\u2116 301854-7 \\\"О внесении изменений в Кодекс Российской Федерации об административных правонарушениях (в части совершенствования механизма пресечения самовольного строительства)\\\" (принят в 1 чтении 23.05.18)\",\n" +
                "         \"voteDate\":\"2021-01-26 13:32:44\",\n" +
                "         \"voteCount\":300,\n" +
                "         \"forCount\":299,\n" +
                "         \"againstCount\":0,\n" +
                "         \"abstainCount\":1,\n" +
                "         \"absentCount\":143,\n" +
                "         \"resultType\":\"количественное\",\n" +
                "         \"result\":true\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\":103963,\n" +
                "         \"subject\":\"(первое чтение) О проекте федерального закона \\u2116 301854-7 \\\"О внесении изменений в Кодекс Российской Федерации об административных правонарушениях (в части совершенствования механизма пресечения самовольного строительства)\\\"\",\n" +
                "         \"voteDate\":\"2018-05-23 14:09:56\",\n" +
                "         \"voteCount\":366,\n" +
                "         \"forCount\":365,\n" +
                "         \"againstCount\":0,\n" +
                "         \"abstainCount\":1,\n" +
                "         \"absentCount\":79,\n" +
                "         \"resultType\":\"количественное\",\n" +
                "         \"result\":true\n" +
                "      }\n" +
                "   ]\n" +
                "}"

        fun getPositiveServerResponse(): GovResponse {
            return Gson().fromJson<GovResponse>(lawsOkV2ApiRes, GovResponse::class.java)
        }

        fun getPositiveButIncompleteServerResponse(): GovResponse {
            return Gson().fromJson(incompleteLawV2ApiRes, GovResponse::class.java)
        }

        fun getPositiveSingleLawServerResponse(): GovResponse {
            val response = Gson().fromJson<GovResponse>(lawsOkV2ApiRes, GovResponse::class.java)
            response.laws = Collections.singletonList(response.laws.get(0))
            return response
        }

        fun getPositiveButIncompleteSingleLawServerResponse(): GovResponse {
            val response = Gson().fromJson<GovResponse>(incompleteLawV2ApiRes, GovResponse::class.java)
            response.laws = Collections.singletonList(response.laws.get(0))
            return response
        }

        fun getPositiveSingleLawWithDeputiesServerResponse(): GovResponse {
            val response = Gson().fromJson<GovResponse>(lawsOkV2ApiRes, GovResponse::class.java)
            response.laws = Collections.singletonList(response.laws.get(1))
            return response
        }

        fun getPositiveWithPayloadVotesBylawResponse(): VotesResponse {
            return Gson().fromJson<VotesResponse>(votesResponse, VotesResponse::class.java)
        }

 /*       public const val lawsOkApiRes =
            "{\n" +
                    "  \"count\": 30795,\n" +
                    "  \"page\": 2,\n" +
                    "  \"wording\": \"Законопроекты, отсортированные по дате последнего события (по убыванию)\",\n" +
                    "  \"laws\": [\n" +
                    "    {\n" +
                    "      \"id\": 33736,\n" +
                    "      \"number\": \"10309-8\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"Об охоте и о сохранении охотничьих ресурсов и о внесении изменений в отдельные законодательные акты Российской Федерации\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/10309-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-11-01\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12534800,\n" +
                    "            \"name\": \"Комитет ГД по экологии, природным ресурсам и охране окружающей среды\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33730,\n" +
                    "      \"number\": \"9734-8\",\n" +
                    "      \"name\": \"О внесении изменений в некоторые законодательные акты Российской Федерации\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9734-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-11-01\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99112983\",\n" +
                    "            \"name\": \"Василенко Дмитрий Юрьевич\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112472\",\n" +
                    "            \"name\": \"Гумерова Лилия Салаватовна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99107923\",\n" +
                    "            \"name\": \"Умаханов Ильяс Магомед-Саламович\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12535000,\n" +
                    "            \"name\": \"Комитет ГД по науке и высшему образованию\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33728,\n" +
                    "      \"number\": \"9719-8\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"О защите конкуренции\\\" и статью 1 Федерального закона \\\"Об основах государственного регулирования торговой деятельности в Российской Федерации\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9719-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-11-01\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12535600,\n" +
                    "            \"name\": \"Комитет ГД по защите конкуренции\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33735,\n" +
                    "      \"number\": \"9769-8\",\n" +
                    "      \"name\": \"О присоединении Российской Федерации к Женевскому акту Лиссабонского соглашения о наименованиях мест происхождения и географических указаниях\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9769-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Предварительное рассмотрение законопроекта, внесенного в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 4,\n" +
                    "          \"name\": \"Принятие профильным комитетом решения о представлении законопроекта в Совет Государственной Думы.\"\n" +
                    "        },\n" +
                    "        \"solution\": \"предложить принять законопроект к рассмотрению\",\n" +
                    "        \"date\": \"2021-10-29\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 6274700,\n" +
                    "            \"name\": \"Комитет ГД по международным делам\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-20\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33713,\n" +
                    "      \"number\": \"6599-8\",\n" +
                    "      \"name\": \"О внесении изменения в статью 22 Федерального закона \\\"О государственном пенсионном обеспечении в Российской Федерации\\\"\",\n" +
                    "      \"comments\": \"о возможности назначения социальной пенсии гражданам из числа малочисленных народов Севера, на основании сведений, содержащихся в списке лиц, относящихся к малочисленным народам Севера\",\n" +
                    "      \"introductionDate\": \"2021-10-22\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/6599-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Предварительное рассмотрение законопроекта, внесенного в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 4,\n" +
                    "          \"name\": \"Принятие профильным комитетом решения о представлении законопроекта в Совет Государственной Думы.\"\n" +
                    "        },\n" +
                    "        \"solution\": \"предложить принять законопроект к рассмотрению\",\n" +
                    "        \"date\": \"2021-10-29\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 10941500,\n" +
                    "            \"name\": \"Комитет ГД по труду, социальной политике и делам ветеранов\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33149,\n" +
                    "      \"number\": \"1140895-7\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"Об организации предоставления государственных и муниципальных услуг\\\" в целях закрепления права заявителя на выбор формы получения государственных и муниципальных услуг\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-04-01\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1140895-7\",\n" +
                    "      \"transcriptUrl\": \"http://api.duma.gov.ru/api/transcript/1140895-7\",\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 6,\n" +
                    "          \"name\": \"Принятие решения ответственным комитетом о представлении законопроекта в Совет Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": \"предложить отклонить законопроект в соответствии с частью 7 статьи 118 Регламента ГД\",\n" +
                    "        \"date\": \"2021-10-29\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99100601\",\n" +
                    "            \"name\": \"Рашкин Валерий Федорович\",\n" +
                    "            \"position\": \"Депутат ГД\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": [\n" +
                    "          {\n" +
                    "            \"id\": \"72100004\",\n" +
                    "            \"name\": \"Фракция Политической партии \\\"КОММУНИСТИЧЕСКАЯ ПАРТИЯ РОССИЙСКОЙ ФЕДЕРАЦИИ\\\"\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 10942500,\n" +
                    "            \"name\": \"Комитет ГД по федеративному устройству и вопросам местного самоуправления\",\n" +
                    "            \"isCurrent\": false,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": \"2021-10-11\"\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 32951,\n" +
                    "      \"number\": \"1098759-7\",\n" +
                    "      \"name\": \"О внесении изменений в Трудовой кодекс Российской Федерации\",\n" +
                    "      \"comments\": \"о дополнительных гарантиях отдельным категориям работников, имеющих несовершеннолетних детей\",\n" +
                    "      \"introductionDate\": \"2021-01-21\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1098759-7\",\n" +
                    "      \"transcriptUrl\": \"http://api.duma.gov.ru/api/transcript/1098759-7\",\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 5,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в третьем чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 12,\n" +
                    "          \"name\": \"Принятие ответственным комитетом решения  о представлении законопроекта в Совет Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": \"предложить принять закон\",\n" +
                    "        \"date\": \"2021-10-29\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99102729\",\n" +
                    "            \"name\": \"Карелова Галина Николаевна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 10941500,\n" +
                    "          \"name\": \"Комитет ГД по труду, социальной политике и делам ветеранов\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2011-12-21\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 10941500,\n" +
                    "            \"name\": \"Комитет ГД по труду, социальной политике и делам ветеранов\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33734,\n" +
                    "      \"number\": \"9761-8\",\n" +
                    "      \"name\": \"О внесении изменения в статью 16 Федерального закона \\\"О пожарной безопасности\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9761-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 10942300,\n" +
                    "            \"name\": \"Комитет ГД по безопасности и противодействию коррупции\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33733,\n" +
                    "      \"number\": \"9760-8\",\n" +
                    "      \"name\": \"О внесении изменений в Воздушный кодекс Российской Федерации\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9760-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12534400,\n" +
                    "            \"name\": \"Комитет ГД по транспорту и развитию транспортной инфраструктуры\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33732,\n" +
                    "      \"number\": \"9758-8\",\n" +
                    "      \"name\": \"О внесении изменения в статью 5 Федерального закона \\\"О науке и государственной научно-технической политике\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9758-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99113292\",\n" +
                    "            \"name\": \"Галушина Римма Федоровна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": true\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112472\",\n" +
                    "            \"name\": \"Гумерова Лилия Салаватовна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12535000,\n" +
                    "            \"name\": \"Комитет ГД по науке и высшему образованию\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33731,\n" +
                    "      \"number\": \"9741-8\",\n" +
                    "      \"name\": \"О внесении изменения в статью 1 Федерального закона \\\"Об общем числе мировых судей и количестве судебных участков в субъектах Российской Федерации\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9741-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 8597500,\n" +
                    "            \"name\": \"Законодательное Собрание Санкт-Петербурга\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1999-01-16\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12194200,\n" +
                    "            \"name\": \"Комитет ГД по государственному строительству и законодательству\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2016-10-05\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33729,\n" +
                    "      \"number\": \"9732-8\",\n" +
                    "      \"name\": \"О внесении изменения в статью 8.32 Кодекса Российской Федерации об административных правонарушениях\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9732-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12194200,\n" +
                    "            \"name\": \"Комитет ГД по государственному строительству и законодательству\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2016-10-05\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33727,\n" +
                    "      \"number\": \"9712-8\",\n" +
                    "      \"name\": \"О внесении изменений в статью 261 Уголовного кодекса Российской Федерации и статьи 150 и 151 Уголовно-процессуального кодекса Российской Федерации\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9712-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12194200,\n" +
                    "            \"name\": \"Комитет ГД по государственному строительству и законодательству\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2016-10-05\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33726,\n" +
                    "      \"number\": \"9703-8\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"О развитии малого и среднего предпринимательства в Российской Федерации\\\"\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-28\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/9703-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12535800,\n" +
                    "            \"name\": \"Комитет ГД по малому и среднему предпринимательству\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2021-10-12\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33724,\n" +
                    "      \"number\": \"8791-8\",\n" +
                    "      \"name\": \"О внесении изменений в Кодекс Российской Федерации об административных правонарушениях\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-10-27\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/8791-8\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"Внесение законопроекта в Государственную Думу\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"Прохождение законопроекта у Председателя Государственной Думы\"\n" +
                    "        },\n" +
                    "        \"solution\": null,\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99100145\",\n" +
                    "            \"name\": \"Жуков Александр Дмитриевич\",\n" +
                    "            \"position\": \"Депутат ГД\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99110037\",\n" +
                    "            \"name\": \"Жуков Андрей Дмитриевич\",\n" +
                    "            \"position\": \"Депутат ГД\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112431\",\n" +
                    "            \"name\": \"Ковитиди Ольга Федоровна\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112004\",\n" +
                    "            \"name\": \"Пушков Алексей Константинович\",\n" +
                    "            \"position\": \"Член СФ\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"id\": \"99112899\",\n" +
                    "            \"name\": \"Ямпольская Елена Александровна\",\n" +
                    "            \"position\": \"Депутат ГД\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": [\n" +
                    "          {\n" +
                    "            \"id\": \"72100024\",\n" +
                    "            \"name\": \"Фракция Всероссийской политической партии \\\"ЕДИНАЯ РОССИЯ\\\"\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": null,\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 12194200,\n" +
                    "            \"name\": \"Комитет ГД по государственному строительству и законодательству\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2016-10-05\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33671,\n" +
                    "      \"number\": \"1258298-7\",\n" +
                    "      \"name\": \"О бюджете Фонда социального страхования Российской Федерации на 2022 год и на плановый период 2023 и 2024 годов\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-09-30\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1258298-7\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Рассмотрение законопроекта Государственной Думой\"\n" +
                    "        },\n" +
                    "        \"solution\": \"принять законопроект в первом чтении; представить поправки к законопроекту \",\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 6276700,\n" +
                    "          \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2000-01-18\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 6276700,\n" +
                    "            \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2000-01-18\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": [\n" +
                    "          {\n" +
                    "            \"id\": 10941500,\n" +
                    "            \"name\": \"Комитет ГД по труду, социальной политике и делам ветеранов\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33670,\n" +
                    "      \"number\": \"1258297-7\",\n" +
                    "      \"name\": \"О бюджете Федерального фонда обязательного медицинского страхования на 2022 год и на плановый период 2023 и 2024 годов\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-09-30\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1258297-7\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Рассмотрение законопроекта Государственной Думой\"\n" +
                    "        },\n" +
                    "        \"solution\": \"представить поправки к законопроекту ; принять законопроект в первом чтении\",\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 6276700,\n" +
                    "          \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2000-01-18\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 6276700,\n" +
                    "            \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2000-01-18\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": [\n" +
                    "          {\n" +
                    "            \"id\": 8634800,\n" +
                    "            \"name\": \"Комитет ГД по охране здоровья\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2003-12-29\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33669,\n" +
                    "      \"number\": \"1258296-7\",\n" +
                    "      \"name\": \"О бюджете Пенсионного фонда Российской Федерации на 2022 год и на плановый период 2023 и 2024 годов\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-09-30\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1258296-7\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Рассмотрение законопроекта Государственной Думой\"\n" +
                    "        },\n" +
                    "        \"solution\": \"принять законопроект в первом чтении; представить поправки к законопроекту \",\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 6276700,\n" +
                    "          \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2000-01-18\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 6276700,\n" +
                    "            \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2000-01-18\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": [\n" +
                    "          {\n" +
                    "            \"id\": 10941500,\n" +
                    "            \"name\": \"Комитет ГД по труду, социальной политике и делам ветеранов\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2011-12-21\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33668,\n" +
                    "      \"number\": \"1258295-7\",\n" +
                    "      \"name\": \"О федеральном бюджете на 2022 год и на плановый период 2023 и 2024 годов\",\n" +
                    "      \"comments\": null,\n" +
                    "      \"introductionDate\": \"2021-09-30\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1258295-7\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Рассмотрение законопроекта Государственной Думой\"\n" +
                    "        },\n" +
                    "        \"solution\": \"принять законопроект в первом чтении; представить поправки к законопроекту \",\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [],\n" +
                    "        \"departments\": [\n" +
                    "          {\n" +
                    "            \"id\": 6230800,\n" +
                    "            \"name\": \"Правительство РФ\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"1994-01-01\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"factions\": []\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 6276700,\n" +
                    "          \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2000-01-18\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 6276700,\n" +
                    "            \"name\": \"Комитет ГД по бюджету и налогам\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2000-01-18\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 33326,\n" +
                    "      \"number\": \"1174700-7\",\n" +
                    "      \"name\": \"О внесении изменений в Федеральный закон \\\"Об электроэнергетике\\\"\",\n" +
                    "      \"comments\": \"в части установления исключительных тарифов на электрическую энергию\",\n" +
                    "      \"introductionDate\": \"2021-05-19\",\n" +
                    "      \"url\": \"http://sozd.parlament.gov.ru/bill/1174700-7\",\n" +
                    "      \"transcriptUrl\": null,\n" +
                    "      \"lastEvent\": {\n" +
                    "        \"stage\": {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"Рассмотрение законопроекта в первом чтении\"\n" +
                    "        },\n" +
                    "        \"phase\": {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Рассмотрение законопроекта Государственной Думой\"\n" +
                    "        },\n" +
                    "        \"solution\": \"отклонить законопроект\",\n" +
                    "        \"date\": \"2021-10-28\",\n" +
                    "        \"document\": null\n" +
                    "      },\n" +
                    "      \"subject\": {\n" +
                    "        \"deputies\": [\n" +
                    "          {\n" +
                    "            \"id\": \"99113205\",\n" +
                    "            \"name\": \"Кузьмин Андрей Альбертович\",\n" +
                    "            \"position\": \"Депутат ГД\",\n" +
                    "            \"isCurrent\": false\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"departments\": [],\n" +
                    "        \"factions\": [\n" +
                    "          {\n" +
                    "            \"id\": \"72100005\",\n" +
                    "            \"name\": \"Фракция Политической партии ЛДПР - Либерально-демократической партии России\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"committees\": {\n" +
                    "        \"responsible\": {\n" +
                    "          \"id\": 9707100,\n" +
                    "          \"name\": \"Комитет ГД по энергетике\",\n" +
                    "          \"isCurrent\": true,\n" +
                    "          \"startDate\": \"2007-12-24\",\n" +
                    "          \"endDate\": null\n" +
                    "        },\n" +
                    "        \"profile\": [\n" +
                    "          {\n" +
                    "            \"id\": 9707100,\n" +
                    "            \"name\": \"Комитет ГД по энергетике\",\n" +
                    "            \"isCurrent\": true,\n" +
                    "            \"startDate\": \"2007-12-24\",\n" +
                    "            \"endDate\": null\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"soexecutor\": []\n" +
                    "      },\n" +
                    "      \"type\": {\n" +
                    "        \"id\": 38,\n" +
                    "        \"name\": \"Федеральный закон\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}"*/
    }
}