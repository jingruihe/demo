-- Lua脚本学习
print('hello world')

-- ############ 变量 ############
-- 默认变量都是全局，不声明变量结果 nil, 删除变量 b=nil
-- 在lua中，只要声明了变量就存在，设置为nil变量就不存在
-- 全局变量
a=5
-- 局部变脸
function test()
    local c=1
end


-- ############ 数据类型 ############
print(type("Hello world"))      --> string
print(type(10.4*3))             --> number
print(type(print))              --> function
print(type(type))               --> function
print(type(true))               --> boolean
print(type(nil))                --> nil
print(type(type(X)))            --> string
-- 此外还有thread，表示执行的独立线路，用于执行协同程序（略）
-- table关联数组，创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表。
print(type({}))



-- ############ 逻辑 ############
-- IF
if false or nil then
    print("至少有一个是 true")
else
    print("false 和 nil 都为 false")
end

-- FOR, 从exp1变化到exp2，每次变化以exp3为步长递增(默认1)
for var=0,10,2 do
    print("循环0-10, 每次递增2:", var)
end

-- FOR迭代器逻辑,访问table可以tab1[key1]。注意table下标k从1开始算.
-- 输出不指定k默认从1开始赋值,打印: 1-王五  key1-张三  key2-李四
tab1 = { key1 = "张三", key2 = "李四", "王五" }
for k, v in pairs(tab1) do
    print(k .. "-" .. v)
end

-- While循环
a=10
while( a < 20 )
do
    print("a 的值为:", a)
    a = a+1
end

-- ############ 函数 ############
-- 可以闭包，也可以用匿名函数（略）
function factorial1(n)
    if n == 0 then
        return 1
    else
        return n * factorial1(n - 1)
    end
end
print(factorial1(5))
factorial2 = factorial1
print(factorial2(5))