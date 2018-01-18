# 3 этап - тестирование с Нагрузкой
Нагрузочное тестирование было проведено с помощью wrk
## После оптимизации 
Добавлен кэш, что даёт огромный прирост в GET с повторами.
### PUT без перезаписи
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.29ms  414.15us  16.74ms   95.06%
    Req/Sec   209.37     12.16   225.00     73.66%
  Latency Distribution
     50%    1.16ms
     75%    1.47ms
     90%    1.57ms
     99%    2.13ms
  12527 requests in 1.00m, 1.12MB read
Requests/sec:    208.49
Transfer/sec:     19.14KB
```

Длительность - 20 минут
```
 wrk --latency -t1 -c1 -d20m -s put
.lua http://localhost:8080
Running 20m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.56ms   11.95ms 427.25ms   99.47%
    Req/Sec   185.13     22.32   232.00     71.13%
  Latency Distribution
     50%    1.83ms
     75%    2.27ms
     90%    2.45ms
     99%    4.57ms
  220772 requests in 20.00m, 19.79MB read
Requests/sec:    183.96
Transfer/sec:     16.89KB
```

##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.11ms    1.38ms  59.12ms   99.16%
    Req/Sec   217.62      9.98   232.00     88.94%
  Latency Distribution
     50%    1.00ms
     75%    1.04ms
     90%    1.11ms
     99%    2.21ms
  26030 requests in 1.00m, 2.33MB read
Requests/sec:    433.24
Transfer/sec:     39.77KB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.80ms    8.72ms 306.21ms   98.99%
    Req/Sec   154.84     17.20   232.00     87.72%
  Latency Distribution
     50%    1.78ms
     75%    2.66ms
     90%    3.71ms
     99%   11.61ms
  184954 requests in 5.00m, 16.58MB read
Requests/sec:    616.33
Transfer/sec:     56.58KB
```
### GET без повторов
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   317.87us  188.86us  11.07ms   93.31%
    Req/Sec     3.08k   684.75     3.87k    50.75%
  Latency Distribution
     50%  265.00us
     75%  366.00us
     90%  443.00us
     99%  622.00us
  184107 requests in 1.00m, 733.57MB read
Requests/sec:   3063.34
Transfer/sec:     12.21MB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   491.27us  137.80us   4.31ms   73.59%
    Req/Sec     2.00k   102.46     2.18k    86.86%
  Latency Distribution
     50%  480.00us
     75%  505.00us
     90%  714.00us
     99%  777.00us
  239384 requests in 1.00m, 0.93GB read
Requests/sec:   3983.04
Transfer/sec:     15.87MB
```
##### 4 потока, 4 соединения
Длительность - 1 минута
```
 wrk --latency -t4 -c4 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     0.94ms  283.47us   5.65ms   76.15%
    Req/Sec     1.06k    46.27     1.20k    74.46%
  Latency Distribution
     50%    0.92ms
     75%    1.15ms
     90%    1.36ms
     99%    1.56ms
  252426 requests in 1.00m, 0.98GB read
Requests/sec:   4205.07
Transfer/sec:     16.75MB
```
### PUT c перезаписью
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_rewr.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.22ms  307.09us  15.78ms   92.13%
    Req/Sec   214.77      8.69   232.00     77.35%
  Latency Distribution
     50%    1.13ms
     75%    1.23ms
     90%    1.51ms
     99%    1.92ms
  12849 requests in 1.00m, 1.15MB read
Requests/sec:    213.93
Transfer/sec:     19.64KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_rewr.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.14ms    1.73ms  66.75ms   98.95%
    Req/Sec   216.63     14.84   232.00     93.12%
  Latency Distribution
     50%    0.99ms
     75%    1.03ms
     90%    1.21ms
     99%    3.10ms
  25928 requests in 1.00m, 2.32MB read
Requests/sec:    431.43
Transfer/sec:     39.61KB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_rewr.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.58ms   27.87ms 696.34ms   99.12%
    Req/Sec   185.67     22.49   225.00     90.22%
  Latency Distribution
     50%    1.22ms
     75%    1.40ms
     90%    1.93ms
     99%   20.07ms
  220796 requests in 5.00m, 19.79MB read
Requests/sec:    735.77
Transfer/sec:     67.54KB
```
### GET с повторами
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    97.22us  137.92us   8.93ms   99.21%
    Req/Sec     9.84k   677.01    10.26k    91.85%
  Latency Distribution
     50%   88.00us
     75%   90.00us
     90%   95.00us
     99%  190.00us
  588497 requests in 1.00m, 2.29GB read
Requests/sec:   9792.05
Transfer/sec:     39.02MB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   151.16us   69.68us   5.08ms   98.28%
    Req/Sec     6.28k   134.63     6.90k    79.28%
  Latency Distribution
     50%  155.00us
     75%  159.00us
     90%  166.00us
     99%  251.00us
  750932 requests in 1.00m, 2.92GB read
Requests/sec:  12494.86
Transfer/sec:     49.79MB
```
##### 4 потока, 4 соединения
Длительность - 1 минута
```
 wrk --latency -t4 -c4 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   149.58us   80.60us   4.32ms   97.07%
    Req/Sec     6.44k   289.50     6.75k    91.22%
  Latency Distribution
     50%  140.00us
     75%  151.00us
     90%  176.00us
     99%  399.00us
  1540263 requests in 1.00m, 5.99GB read
Requests/sec:  25628.48
Transfer/sec:    102.12MB
```
### Смесь PUT/GET 50/50 без перезаписи
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     0.98ms  620.35us  11.83ms   91.27%
    Req/Sec   374.50     12.61   404.00     80.07%
  Latency Distribution
     50%    1.34ms
     75%    1.47ms
     90%    1.53ms
     99%    1.99ms
  22403 requests in 1.00m, 45.63MB read
  Non-2xx or 3xx responses: 1
Requests/sec:    372.95
Transfer/sec:    777.93KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.46ms    1.57ms  56.77ms   99.14%
    Req/Sec   316.98     14.49   360.00     87.52%
  Latency Distribution
     50%    1.38ms
     75%    1.99ms
     90%    2.19ms
     99%    2.88ms
  37903 requests in 1.00m, 77.21MB read
  Non-2xx or 3xx responses: 1
Requests/sec:    631.25
Transfer/sec:      1.29MB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_get.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.35ms    4.88ms 241.08ms   99.33%
    Req/Sec   253.63     23.45   336.00     80.57%
  Latency Distribution
     50%    1.92ms
     75%    2.92ms
     90%    3.67ms
     99%    5.41ms
  303137 requests in 5.00m, 617.50MB read
  Non-2xx or 3xx responses: 1
Requests/sec:   1010.12
Transfer/sec:      2.06MB
```
### Смесь PUT/GET 50/50 с перезаписью
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.02ms    5.48ms 174.97ms   99.69%
    Req/Sec   408.08     16.94   424.00     94.59%
  Latency Distribution
     50%    1.01ms
     75%    1.10ms
     90%    1.13ms
     99%    1.46ms
  24381 requests in 1.00m, 49.67MB read
Requests/sec:    406.21
Transfer/sec:    847.36KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.06ms  739.80us  17.25ms   80.84%
    Req/Sec   356.59     36.15   424.00     66.64%
  Latency Distribution
     50%    1.03ms
     75%    1.36ms
     90%    1.79ms
     99%    2.46ms
  42643 requests in 1.00m, 86.87MB read
Requests/sec:    710.31
Transfer/sec:      1.45MB

```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_get_repeat.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.11ms    4.42ms 240.64ms   99.01%
    Req/Sec   267.30     31.26   350.00     73.45%
  Latency Distribution
     50%    1.67ms
     75%    2.55ms
     90%    3.42ms
     99%    6.46ms
  319417 requests in 5.00m, 650.67MB read
Requests/sec:   1064.42
Transfer/sec:      2.17MB
```
