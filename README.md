# government-rus
A mobile app to track documents that will be discussed and applied by government

1. Get a list of sessions (заседаний):
```
http://api.duma.gov.ru/api/<api_key>/questions.json?app_token=<app_token>&limit=5&dateFrom=2020-01-01
```
(link http://api.duma.gov.ru/pages/dokumentatsiya/voprosi-zasedaniy-gosudarstvennoy-dumi)

Response: 
```
{
   "name":"О проекте порядка работы Государственной Думы на 27 мая 2020 года.",
   "datez":"2020-05-27 00:00:00",
   "kodz":1847,
   "kodvopr":1,
   "nbegin":389,
   "nend":618
}
```

2. Get transcript of an issue (стенограмма заседания по вопросу):
```
http://api.duma.gov.ru/api/<api_key>/<kodz>/<kodvopr>.json?app_token=<app_token>
```
(link http://api.duma.gov.ru/pages/dokumentatsiya/stenogramma-rassmotreniya-voprosa)

Response: 
```
{
   "meetings":[
      {
         "date":"2020-05-27",
         "number":299,
         "maxNumber":4795,
         "questions":[
            {
               "name":"О проекте порядка работы Государственной Думы на 27 мая 2020 года.",
               "stage":null,
               "parts":[
                  {
                     "startLine":389,
                     "endLine":618,
                     "type":null,
                     "lines":[
                        "<текст заседания>",
                        "<текст заседания>"
                     ],
                     "votes":[
                        {
                           "line":425,
                           "date":"2020-05-27 12:03:32",
                           "id":111012
                        },
                        {
                           "line":483,
                           "date":"2020-05-27 12:06:12",
                           "id":111013
                        },
                        {
                           "line":610,
                           "date":"2020-05-27 12:14:03",
                           "id":111014
                        }
                     ]
                  }
               ]
            }
         ]
      }
   ]
}
```
3. Get a votes for issue: 
```
http://api.duma.gov.ru/api/<api_key>/vote/<id (vote id)>.json?app_token=<app_token>
```
(link http://api.duma.gov.ru/pages/dokumentatsiya/svedeniya-o-golosovanii)

Response:
```
{
   "date":"2020-05-27 12:14:03",
   "lawNumber":null,
   "subject":"(в целом) О проекте порядка работы Государственной Думы на 27 мая 2020 года",
   "resolution":false,
   "for":"330",
   "against":"0",
   "abstain":"0",
   "absent":"120",
   "transcriptLink":"http:\/\/api.duma.gov.ru\/api\/transcriptFull\/2020-05-27#2020_05_27_12_14_03",
   "resultsByFaction":[
      {
         "code":"72100011",
         "total":"3",
         "for":"3",
         "against":"0",
         "abstain":"0",
         "name":"Депутаты, не входящие во фракции",
         "abbr":"Вне фракций"
      }
   ],
   "resultsByDeputy":[
      {
         "code":"99111893",
         "result":"absent",
         "factionCode":"72100005",
         "family":"Маринин",
         "name":"Сергей",
         "patronymic":"Владимирович",
         "resultSort":"2",
         "faction":"ЛДПР"
      }
   ]
}
```
4. Get information about a deputy: 
```
http://api.duma.gov.ru/api/<api_key>/deputy.json?id=<code (deputy code)>&app_token=<app_token>
```
(link http://api.duma.gov.ru/pages/dokumentatsiya/svedeniya-o-deputate)

5. Laws signed by President: 
```
http://api.duma.gov.ru/api/<api_key>/search.json?status=7&app_token=<app_token>
```
Response
```
{
   "count":8340,
   "page":1,
   "wording":"Законопроекты, подписанные Президентом РФ, отсортированные по дате последнего события (по убыванию)",
   "laws":[
      {
         "id":32170,
         "number":"953580-7",
         "name":"О внесении изменений в отдельные законодательные акты Российской Федерации в целях принятия неотложных мер, направленных на обеспечение устойчивого развития экономики и предотвращение последствий распространения новой коронавирусной инфекции",
         "comments":null,
         "introductionDate":"2020-05-07",
         "url":"http:\/\/sozd.parlament.gov.ru\/bill\/953580-7",
         "transcriptUrl":"http:\/\/api.duma.gov.ru\/api\/transcript\/953580-7",
         "lastEvent":{
            "stage":{
               "id":8,
               "name":"Прохождение закона у Президента Российской Федерации"
            },
            "phase":{
               "id":23,
               "name":"Рассмотрение закона Президентом Российской Федерации"
            },
            "solution":"закон подписан",
            "date":"2020-06-08",
            "document":null
         },
         "subject":{
            "deputies":[

            ],
            "departments":[
               {
                  "id":6230800,
                  "name":"Правительство РФ",
                  "isCurrent":true,
                  "startDate":"1994-01-01",
                  "endDate":null
               }
            ],
            "factions":[

            ]
         },
         "committees":{
            "responsible":{

            },
            "profile":[

            ],
            "soexecutor":[

            ]
         },
         "type":{
            "id":38,
            "name":"Федеральный закон"
         }
      }
   ]
}
```
