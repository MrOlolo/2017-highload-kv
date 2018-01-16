wrk.method = "PUT"
methodCnt = 0
counter = 0
repeatCycle = 837

request = function()
   path = "/v0/entity?id=" .. counter
   if ((methodCnt % 2)==0) then
      wrk.method = "PUT"
      value = ""
      for i = 1, 4100 do
         value = value .. string.char(math.random(48, 122))          
      end
      wrk.body = value
      methodCnt = methodCnt +1 
   else
      wrk.method = "GET"
      counter = (counter + 1) % 837
      methodCnt = methodCnt +1 
   end

   return wrk.format(nil, path)

end
