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

написал сборщик статистики для оценки влияния на отзывчивость приложения
оказалось - что несмотря на разное время работы коллекторов - практически на отзывчивость они сильно
не влияют
по времени работы самих процессов GC - меньше всего параллельный (аналогично G1),
 посередине - сериал, а больше всего CMS. При этом в части замедления работы приложения 
 не заметил разницы. 

 > Task :HW07-GC:ExternalProgramLauncher.main()
test 10 times GC for collector -XX:+UseSerialGC
app exit code is 0.app time is 8s. total GC time is 1.7450421 s
app exit code is 0.app time is 7s. total GC time is 1.6316372 s
app exit code is 0.app time is 7s. total GC time is 1.6357148 s
app exit code is 0.app time is 7s. total GC time is 1.6301228 s
app exit code is 0.app time is 7s. total GC time is 1.6619272 s
app exit code is 0.app time is 7s. total GC time is 1.647958 s
app exit code is 0.app time is 7s. total GC time is 1.6209489 s
app exit code is 0.app time is 7s. total GC time is 1.6499752 s
app exit code is 0.app time is 8s. total GC time is 1.654742 s
app exit code is 0.app time is 7s. total GC time is 1.6553925 s
test 10 times GC for collector -XX:+UseParallelGC
app exit code is 0.app time is 9s. total GC time is 0.286823 s
app exit code is 0.app time is 9s. total GC time is 0.258923 s
app exit code is 0.app time is 8s. total GC time is 0.26311702 s
app exit code is 0.app time is 8s. total GC time is 0.24246399 s
app exit code is 0.app time is 8s. total GC time is 0.253991 s
app exit code is 0.app time is 8s. total GC time is 0.30108503 s
app exit code is 0.app time is 8s. total GC time is 0.2514 s
app exit code is 0.app time is 8s. total GC time is 0.243519 s
app exit code is 0.app time is 9s. total GC time is 0.29024002 s
app exit code is 0.app time is 8s. total GC time is 0.26539302 s
test 10 times GC for collector -XX:+UseConcMarkSweepGC
app exit code is 0.app time is 7s. total GC time is 5.239915 s
app exit code is 0.app time is 8s. total GC time is 2.0916579 s
app exit code is 0.app time is 8s. total GC time is 3.3966696 s
app exit code is 0.app time is 7s. total GC time is 5.3493404 s
app exit code is 0.app time is 7s. total GC time is 5.256366 s
app exit code is 0.app time is 7s. total GC time is 2.3585079 s
app exit code is 0.app time is 7s. total GC time is 4.665426 s
app exit code is 0.app time is 7s. total GC time is 5.1707273 s
app exit code is 0.app time is 7s. total GC time is 5.2615113 s
app exit code is 0.app time is 7s. total GC time is 1.7949953 s
test 10 times GC for collector -XX:+UseG1GC
app exit code is 0.app time is 8s. total GC time is 0.26817998 s
app exit code is 0.app time is 8s. total GC time is 0.255229 s
app exit code is 0.app time is 8s. total GC time is 0.20445599 s
app exit code is 0.app time is 9s. total GC time is 0.25433 s
app exit code is 0.app time is 8s. total GC time is 0.246322 s
app exit code is 0.app time is 9s. total GC time is 0.242815 s
app exit code is 0.app time is 9s. total GC time is 0.234508 s
app exit code is 0.app time is 10s. total GC time is 0.24494301 s
app exit code is 0.app time is 8s. total GC time is 0.23332995 s
app exit code is 0.app time is 8s. total GC time is 0.22538799 s
test 10 times GC for collector -XX:+UnlockExperimentalVMOptions -XX:+UseZGC
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.005s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.005s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.007s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.007s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.006s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.004s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.004s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.005s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.008s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported
app exit code is 1.app time is 0s. total GC time is 0.0 s
see app log below:
[0.006s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
Error occurred during initialization of VM
Option -XX:+UseZGC not supported

Deprecated Gradle features were used in this build, making it incompatible with Gradle 7.0.
Use '--warning-mode all' to show the individual deprecation warnings.
See https://docs.gradle.org/6.3/userguide/command_line_interface.html#sec:command_line_warnings

BUILD SUCCESSFUL in 5m 38s
2 actionable tasks: 2 executed
1:41:15: Task execution finished 'ExternalProgramLauncher.main()'.
