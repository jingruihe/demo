package.path = package.path..";E:/env/lua/redis-lua-version-2.0/src/?.lua"  --redis.lua所在目录
local redis = require("redis")
local client = redis.connect('192.168.1.121',16379)
client:auth('qishudi')
client:select(1)
client:set("testKey","test lua")
local a = client:get('testKey')
print(a)