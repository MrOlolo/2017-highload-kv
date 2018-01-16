
wrk.method = "GET"
counter = 0
repeatCycle = 837

request = function()
   path = "/v0/entity?id=" .. counter
   counter = (counter + 1) % repeatCycle
   return wrk.format(nil, path)
end
