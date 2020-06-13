# government-rus
A mobile app to track documents that will be discussed and applied by government

1. Get a list of events (заседаний):
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

2. Get stenogram of an issue (стенограмма заседания по вопросу):
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
