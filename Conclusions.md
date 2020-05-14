проверил на SimpleApplication
4 вида GC - Serial Parralel G1 CMS
ZGC not supported в oracle jdk13, не знаю как его проверить, наверное он не очень популярен

jvm opts	-verbose:gc	-verbose:gc	-verbose:gc	-verbose:gc	
	-XX:+UseSerialGC	-XX:+UseParallelGC	-XX:+UseConcMarkSweepGC	-XX:+UseG1GC	Option -XX:+UseZGC not supported
	-Xms256m	-Xms256m	-Xms256m	-Xms256m	
	-Xmx4192m	-Xmx4192m	-Xmx4192m	-Xmx4192m	

app time, sec	123	115	125	125	
gc/total jvm cpu usage %	4,56	43,92	17,95	20,00	
gc time, sec	15,5	9	5	8,8	
gc time, %	12,60	7,83	4,00	7,04	
gc collections	15	17	56	58	
eden time, sec	10	3	5	3,8	
eden time, %	8,13	2,61	4,00	3,04	
eden collections	10	9	56	52	
old gen time, sec	5,5	6	0	5	
old gen time, %	4,47	5,22	0,00	4,00	
old gen collections	5	8	0	6	

вывод: 
выбор GC зависит от структуры и функциональности приложения, чувствительности к отклику.
заметил что Serial и Parallel делают чистку и из eden и из old ритмично
в то время как G1 и  CMS в основном скидывают все из eden в old и как заполнится old - начинают ее чистить

для нашего приложения (постоянная накачка данных в кучу) лучше будет Parallel, т.к. он при несильных 
временных затратах (8% времени приложения ушло на чистку и STW) обеспечивает нам баланс 
свободного места в куче при допустимом отклике (приложение с ним выполнилось быстрее остальных)
но при этом он потребляет процессора раза в 3 больше остальных, хотя и не так много.

G1 CMS - показали примерно одинаковую логику, поэтому в случае большего выделения оперативки под xmx - 
они будут оптимальнее в части расхода процессора


   
