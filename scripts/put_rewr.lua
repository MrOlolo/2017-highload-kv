
wrk.method = "PUT"
counter = 0
repeatCycle = 837

request = function()
   path = "/v0/entity?id=" .. counter
   value = ""
   for i = 1, 4100 do
      value = value .. string.char(math.random(48, 122))
   end
   wrk.body = value
   counter = (counter + 1) % repeatCycle
   return wrk.format(nil, path)
end
