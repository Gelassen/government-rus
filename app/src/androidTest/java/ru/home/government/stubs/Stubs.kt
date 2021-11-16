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

        const val anotherLawResponse = "{\n" +
                "   \"count\":1,\n" +
                "   \"page\":1,\n" +
                "   \"wording\":\"Законопроекты, где номер \\\"301854-7\\\", отсортированные по дате последнего события (по убыванию)\",\n" +
                "   \"laws\":[\n" +
                "      {\n" +
                "         \"id\":29254,\n" +
                "         \"number\":\"301854-7\",\n" +
                "         \"name\":\"О внесении изменений в Кодекс Российской Федерации об административных правонарушениях (в части совершенствования механизма пресечения самовольного строительства)\",\n" +
                "         \"comments\":null,\n" +
                "         \"introductionDate\":\"2017-11-01\",\n" +
                "         \"url\":\"http:\\/\\/sozd.parlament.gov.ru\\/bill\\/301854-7\",\n" +
                "         \"transcriptUrl\":\"http:\\/\\/api.duma.gov.ru\\/api\\/transcript\\/301854-7\",\n" +
                "         \"lastEvent\":{\n" +
                "            \"stage\":{\n" +
                "               \"id\":4,\n" +
                "               \"name\":\"Рассмотрение законопроекта во втором чтении\"\n" +
                "            },\n" +
                "            \"phase\":{\n" +
                "               \"id\":11,\n" +
                "               \"name\":\"Рассмотрение законопроекта Государственной Думой\"\n" +
                "            },\n" +
                "            \"solution\":\"отклонить законопроект, принятый в первом чтении\",\n" +
                "            \"date\":\"2021-01-26\",\n" +
                "            \"document\":null\n" +
                "         },\n" +
                "         \"subject\":{\n" +
                "            \"deputies\":[\n" +
                "               \n" +
                "            ],\n" +
                "            \"departments\":[\n" +
                "               {\n" +
                "                  \"id\":6230800,\n" +
                "                  \"name\":\"Правительство РФ\",\n" +
                "                  \"isCurrent\":true,\n" +
                "                  \"startDate\":\"1994-01-01\",\n" +
                "                  \"endDate\":null\n" +
                "               }\n" +
                "            ],\n" +
                "            \"factions\":[\n" +
                "               \n" +
                "            ]\n" +
                "         },\n" +
                "         \"committees\":{\n" +
                "            \"responsible\":{\n" +
                "               \"id\":12194200,\n" +
                "               \"name\":\"Комитет ГД по государственному строительству и законодательству\",\n" +
                "               \"isCurrent\":true,\n" +
                "               \"startDate\":\"2016-10-05\",\n" +
                "               \"endDate\":null\n" +
                "            },\n" +
                "            \"profile\":[\n" +
                "               {\n" +
                "                  \"id\":12194200,\n" +
                "                  \"name\":\"Комитет ГД по государственному строительству и законодательству\",\n" +
                "                  \"isCurrent\":true,\n" +
                "                  \"startDate\":\"2016-10-05\",\n" +
                "                  \"endDate\":null\n" +
                "               }\n" +
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

        const val anotherVotesResponse = "{\n" +
                "   \"totalCount\":\"2\",\n" +
                "   \"page\":\"1\",\n" +
                "   \"pageSize\":\"20\",\n" +
                "   \"wording\":\"Результаты голосования по законопроекту \\\"301854-7\\\", вынесенному для открытого голосования за 01.01.1970.\",\n" +
                "   \"votes\":[\n" +
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

        fun getAnotherPositiveSingleLawResponse(): GovResponse {
            return Gson().fromJson(anotherLawResponse, GovResponse::class.java)
        }

        fun getAnotherPositiveWithPayloadVotesByLawResponse(): VotesResponse {
            return Gson().fromJson<VotesResponse>(anotherVotesResponse, VotesResponse::class.java)
        }

    }
}