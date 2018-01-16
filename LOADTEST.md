# 3 этап - тестирование с Нагрузкой
Нагрузочное тестирование было проведено с помощью wrk
## До оптимизации

### PUT без перезаписи
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.81ms  736.70us  28.60ms   98.34%
    Req/Sec   189.52     10.77   207.00     83.53%
  Latency Distribution
     50%    1.71ms
     75%    1.82ms
     90%    1.99ms
     99%    3.12ms
  11337 requests in 1.00m, 1.02MB read
Requests/sec:    188.82
Transfer/sec:     17.33KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.42ms    1.07ms  48.88ms   99.09%
    Req/Sec   201.62      8.36   212.00     85.58%
  Latency Distribution
     50%    1.33ms
     75%    1.39ms
     90%    1.48ms
     99%    2.44ms
  24153 requests in 1.00m, 2.17MB read
Requests/sec:    401.95
Transfer/sec:     36.90KB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     8.75ms   39.78ms 851.51ms   96.96%
    Req/Sec   152.12     29.60   191.00     80.79%
  Latency Distribution
     50%    2.51ms
     75%    3.31ms
     90%    4.65ms
     99%  226.43ms
  177744 requests in 5.00m, 15.93MB read
Requests/sec:    592.32
Transfer/sec:     54.37KB
```
### GET без повторов
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.62ms    2.13ms  13.78ms   80.44%
    Req/Sec     1.77k     2.17k    5.70k    83.00%
  Latency Distribution
     50%  257.00us
     75%    2.89ms
     90%    5.39ms
     99%    7.02ms
  105522 requests in 1.00m, 184.02MB read
  Non-2xx or 3xx responses: 60734
Requests/sec:   1755.96
Transfer/sec:      3.06MB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
$ wrk --latency -t2 -c2 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.85ms    3.26ms  34.02ms   86.25%
    Req/Sec     1.70k     1.13k    3.21k    44.50%
  Latency Distribution
     50%  473.00us
     75%  727.00us
     90%    6.97ms
     99%   13.31ms
  202511 requests in 1.00m, 367.25MB read
  Non-2xx or 3xx responses: 112934
Requests/sec:   3371.95
Transfer/sec:      6.12MB
```
##### 4 потока, 4 соединения
Длительность - 1 минута
```
 wrk --latency -t4 -c4 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    10.23ms   45.82ms 507.35ms   95.49%
    Req/Sec     1.02k   246.51     1.59k    78.32%
  Latency Distribution
     50%    0.97ms
     75%    1.23ms
     90%    1.54ms
     99%  272.58ms
  234698 requests in 1.00m, 718.92MB read
  Non-2xx or 3xx responses: 55543
Requests/sec:   3908.44
Transfer/sec:     11.97MB
```
### PUT c перезаписью
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_rewr.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.24ms  281.06us  11.86ms   91.89%
    Req/Sec   209.41     13.68   227.00     87.90%
  Latency Distribution
     50%    1.15ms
     75%    1.29ms
     90%    1.50ms
     99%    2.01ms
  12532 requests in 1.00m, 1.12MB read
Requests/sec:    208.65
Transfer/sec:     19.15KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_rewr.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.08ms  671.03us  26.39ms   99.03%
    Req/Sec   217.40     11.26   232.00     87.34%
  Latency Distribution
     50%    1.00ms
     75%    1.03ms
     90%    1.19ms
     99%    1.74ms
  26009 requests in 1.00m, 2.33MB read
Requests/sec:    432.84
Transfer/sec:     39.73KB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_rewr.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.42ms   38.61ms 866.39ms   97.76%
    Req/Sec   182.79     26.61   210.00     91.40%
  Latency Distribution
     50%    1.26ms
     75%    1.44ms
     90%    2.16ms
     99%  198.23ms
  214914 requests in 5.00m, 19.27MB read
Requests/sec:    716.15
Transfer/sec:     65.74KB
```
### GET с повторами
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   249.45us   47.19us   4.20ms   94.38%
    Req/Sec     3.84k   187.67     4.01k    94.67%
  Latency Distribution
     50%  240.00us
     75%  252.00us
     90%  271.00us
     99%  360.00us
  228957 requests in 1.00m, 0.89GB read
Requests/sec:   3815.93
Transfer/sec:     15.20MB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   490.52us  178.16us  11.60ms   75.61%
    Req/Sec     2.01k    85.43     2.23k    72.67%
  Latency Distribution
     50%  476.00us
     75%  514.00us
     90%  703.00us
     99%  764.00us
  239879 requests in 1.00m, 0.93GB read
Requests/sec:   3997.87
Transfer/sec:     15.93MB
```
##### 4 потока, 4 соединения
Длительность - 1 минута
```
 wrk --latency -t4 -c4 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    20.14ms   66.47ms 656.40ms   91.91%
    Req/Sec     0.89k   252.90     1.15k    89.97%
  Latency Distribution
     50%    1.04ms
     75%    1.40ms
     90%   48.05ms
     99%  334.06ms
  193635 requests in 1.00m, 771.53MB read
Requests/sec:   3226.89
Transfer/sec:     12.86MB
```
### Смесь PUT/GET 50/50 без перезаписи
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     0.97ms  615.82us  17.72ms   90.99%
    Req/Sec   371.98      8.71   383.00     79.43%
  Latency Distribution
     50%    1.33ms
     75%    1.49ms
     90%    1.54ms
     99%    1.83ms
  22241 requests in 1.00m, 45.31MB read
Requests/sec:    370.43
Transfer/sec:    772.73KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.46ms    1.34ms  58.73ms   99.18%
    Req/Sec   314.04     12.75   363.00     87.96%
  Latency Distribution
     50%    1.41ms
     75%    1.98ms
     90%    2.23ms
     99%    2.66ms
  37561 requests in 1.00m, 76.52MB read
Requests/sec:    625.62
Transfer/sec:      1.27MB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_get.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.56ms   27.97ms 521.62ms   97.96%
    Req/Sec   255.36     34.19   343.00     84.91%
  Latency Distribution
     50%    1.81ms
     75%    2.83ms
     90%    3.72ms
     99%  157.25ms
  301052 requests in 5.00m, 613.26MB read
Requests/sec:   1003.26
Transfer/sec:      2.04MB
```
### Смесь PUT/GET 50/50 с перезаписью
##### 1 поток, 1 соединение
Длительность - 1 минута
```
 wrk --latency -t1 -c1 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   819.16us  584.06us  18.51ms   97.17%
    Req/Sec   392.09     21.40   424.00     86.91%
  Latency Distribution
     50%    1.03ms
     75%    1.13ms
     90%    1.19ms
     99%    1.76ms
  23421 requests in 1.00m, 47.71MB read
Requests/sec:    390.15
Transfer/sec:    813.86KB
```
##### 2 потока, 2 соединения
Длительность - 1 минута
```
 wrk --latency -t2 -c2 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.16ms    1.05ms  33.25ms   94.58%
    Req/Sec   333.17     47.87   420.00     73.74%
  Latency Distribution
     50%    1.05ms
     75%    1.43ms
     90%    1.94ms
     99%    4.44ms
  39837 requests in 1.00m, 81.15MB read
Requests/sec:    663.08
Transfer/sec:      1.35MB
```
##### 4 потока, 4 соединения
Длительность - 5 минут
```
 wrk --latency -t4 -c4 -d5m -s put_get_repeat.lua http://localhost:8080
Running 5m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.55ms   31.07ms 690.68ms   98.13%
    Req/Sec   263.79     37.56   356.00     84.12%
  Latency Distribution
     50%    1.70ms
     75%    2.63ms
     90%    3.47ms
     99%  150.65ms
  311166 requests in 5.00m, 633.86MB read
Requests/sec:   1036.86
Transfer/sec:      2.11MB
```