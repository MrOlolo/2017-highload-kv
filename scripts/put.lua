-- example dynamic request script which demonstrates changing
-- the request path and a header for each request
-------------------------------------------------------------
-- NOTE: each wrk thread has an independent Lua scripting
-- context and thus there will be one counter per thread

wrk.method = "PUT"
counter = 0

request = function()
   path = "/v0/entity?id=" .. counter
   value = ""
   for i = 1, 4100 do
      value = value .. string.char(math.random(48, 122))
   end
   wrk.body = value
   counter = counter + 1
   return wrk.format(nil, path)
end
