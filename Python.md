## Python 学习笔记

#### 字符编码
- 在计算机内存中，统一使用Unicode编码，当需要保存到硬盘或者需要传输的时候，就转换为UTF-8编码。
- ord()和chr()函数，可以把字母和对应的数字相互转换
- 字符串'xxx'虽然是ASCII编码，但也可以看成是UTF-8编码，而u'xxx'则只能是Unicode编码。
- 把u'xxx'转换为UTF-8编码的'xxx'用encode('utf-8')方法：
```python
u'ABC'.encode('utf-8')
u'中文'.encode('utf-8')
```
- 英文字符转换后表示的UTF-8的值和Unicode值相等（但占用的存储空间不同），而中文字符转换后1个Unicode字符将变为3个UTF-8字符
- len()函数可以返回字符串的长度
- 把UTF-8编码表示的字符串'xxx'转换为Unicode字符串u'xxx'用decode('utf-8')方法
```python
'abc'.decode('utf-8')
print '\xe4\xb8\xad\xe6\x96\x87'.decode('utf-8')
```
- 由于Python源代码也是一个文本文件，所以，当你的源代码中包含中文的时候，在保存源代码时，就需要务必指定保存为UTF-8编码。当Python解释器读取源代码时，为了让它按UTF-8编码读取，我们通常在文件开头写上这两行：
```python
#!/usr/bin/env/ python
#-*- coding: utf-8 -*-
```
#### 格式化
- %运算符就是用来格式化字符串的。在字符串内部，%s表示用字符串替换，%d表示用整数替换，有几个%?占位符，后面就跟几个变量或者值，顺序要对应好。如果只有一个%?，括号可以省略。
```python
'hi,%s,you have $%d.'%('michael',1000000)```

- %d:整数  %s:字符串 %f:浮点数 %x:十六进制整数
- 字符串里面的%是一个普通字符怎么办？这个时候就需要转义，用%%来表示一个%
- 在Python 3.x版本中，把'xxx'和u'xxx'统一成Unicode编码，即写不写前缀u都是一样的，而以字节形式表示的字符串则必须加上b前缀：b'xxx'
#### python 中的列表
**1.list**
- 表名= [元素1,元素2,...]
   ps:元素可以是不同类型，变长,索引访问，从0开始
```python
list=['xiaoma',123,True,['asp',12],'abc']
list[0]//获取第一个元素
list[-1]//获取最后一个元素，倒数第一的意思，以此类推
len(list)//获得元素个数
list.append('2b')//末尾追加元素
list.insert(1,'jack')//在位置1出插入元素
list.pop()//删除末尾元素
list.pop(i)//删除i位置的元素
list[1]='sara'//直接替换位置1的元素
list[3][0]//访问‘asp'```

**2.tuple**
- tuple一旦初始化不能更改，所以没有append、insert方法
- 表名=（元素1，元素2，...）
- 一个元素的tuple定义时：t=(1,) 以消除数学上的歧义 
```python
t=('1','abc',['x','y'])//定义一个内部含list的tuple
t[2][0]='m'
t[2][1]='n'
t
//输出结果为(‘1’，‘abc',['m','n'])，因为list是可变的
//访问元素用方括号```
#### 条件判断和循环
**1.if条件语句**
- if语句执行是从上往下判断，如果在某个判断上是True，把该判断对应的语句执行后，就忽略掉剩下的elif和else
- if<条件判断1>：
    <执行1>
  elif<条件判断2>：
    <执行2>
  elif<条件判断3>：
    <执行3>
  else:
    <执行4>
- if x:
      print 'True'
  只要x是非零数值、非空字符串、非空list，就判断为True,否则为False。
**2.循环**
#####2.1 for in循环
for x in ...//把每个元素循环带入变量x,执行缩进语句块

依次把list或tuple中的每个元素迭代出来
```python 
names=['x','y','z']
for name in names:
	print name 
```
- range(101):产生从0-100的整数序列

```python
sum=0
for x in range(101):
    sum=sum+x
print sum//计算0-100的和
```

#####2.2 while循环
```python
sum=0
n=99
while n>0:
    sum=sum+n
    n=n-2
print sum//计算100以内的奇数和
```

**tip：**从raw_input()读取的内容永远以字符串的形式返回，把字符串和整数比较就不会得到期待的结果，必须先用int()把字符串转换为我们想要的整型：
```python
x=int(raw_input())
```
#### dict和set
**dict**
- 语法：字典名={key:value,key:value,...}
- 查找对应键的值:字典名[key]
- 还可以通过key放入值：d[key]=x  多次复制后，后面会把前面的值覆盖掉
- 判断key是否存在的方法：
  1.key in 字典名
  2.字典名.get(key) 或者自己指定返回值 字典名.get(key,返回值)
 注：使用默认返回时，命令行不提示信息
- 删除key:pop(key)  对应的值也会删除
- dict是一种时间换空间的方法
- key 必须是不可变对象(比如list是可变的，就不能作为key)
- 查找和插入的速度快
- 貌似不能添加？key?
- 重复的键值对会被自动忽略
**set**
- set 相当于是一个数学上的无序无重复元素的集合
- 语法：s=set([1,2,3])  注：需要一个list作为输入集合
- 重复元素被自动过滤
- s.add(key)  添加元素
- s.remove(key) 删除元素
- 两个list可以作 交和并 
  s1&s2   s1|s2
- 不能放不可变对象
#### 不可变对象
对于不变对象来说，调用对象自身的任意方法，也不会改变该对象自身的内容。相反，这些方法会创建新的对象并返回，这样，就保证了不可变对象本身永远是不可变的。
注：字符串对象的replace方法和list的sort方法
    tuple是不可变对象
#### 函数

```python
def my_abs(x):
    if not isinstance(x,(int,float)):
        raise TypeError('bad operand type')
    if x>=0:
        return x
    if x<0:
        return -x
x=int(raw_input('enter your variable:'))
print myabs(x)
```
- def关键字定义函数，函数名，参数个数，冒号
- 有必要，需要对参数类型做检查
- return返回函数结果 没有返回结果自动return none
- 函数可以同时返回多个值，多个值放在一个tuple里面
- 空函数，用来占位

```python
def nop():
    pass
```
#### 函数的参数
**1.默认参数**
```python
def power(x, n=2):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s
power(5)//计算5的平方
power(5,3)//计算5的立方
```
- 默认参数可以简化函数的调用（例子中n=2即为默认参数
- 必选参数在前，默认参数在后
- 当函数有多个参数时，变化大的参数放在前面，变化小的参数放在后面，变化小的参数就可以作为默认参数
- 默认参数降低了函数调用的难度，而需要复杂调用时，可以仅通过传递更多的参数来实现
- 默认参数必须指向不可变对象

```python
def add_end(L=None):
    if L is None:
        L = []
    L.append('END')
    return L
//默认参数的坑
```
**2.可变参数**

- 可变参数:传入的参数的个数可以是任意多个

```python
//计算平方和
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum

calc(1,2,3)//正常调用
calc()//传入参数可以是0个
nums=[1,3,4]
calc(*nums)//如果已经有一个tuple或者list,那么可以j加*号，把它们的元素变成可变参数穿进去

```

- 可变参数函数内部，numbers接受到的的一个tuple

**3.关键字参数**
关键字参数，用来扩展函数的性能
- 关键字参数允许传入任意个数带参数名的参数，这些关键字参数在函数内部自动组合成一个dict

```python
def person(name, age, **kw):
    print 'name:', name, 'age:', age, 'other:', kw
person('Bob', 35, city='Beijing')
//输出：name: Bob age: 35 other: {'city': 'Beijing'}
person('Adam', 45, gender='M', job='Engineer')
 kw = {'city': 'Beijing', 'job': 'Engineer'}
 person('Jack', 24, **kw)//把一个已存在的dict作为关键字参数传进去
```

**4.参数组合**

- 以上参数可以自由组合使用，但是顺序必须是必选参数、默认参数、可变参数和关键字参数

```python
def func(a, b, c=0, *args, **kw):
    print 'a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw
 func(1, 2, 3, 'a', 'b', x=99)
a = 1 b = 2 c = 3 args = ('a', 'b') kw = {'x': 99}
>>> args = (1, 2, 3, 4)
>>> kw = {'x': 99}
>>> func(*args, **kw)
a = 1 b = 2 c = 3 args = (4,) kw = {'x': 99}
```
- 默认参数一定要用不可变对象
- *args是可变参数，args接收的是一个tuple
- **kw是关键字参数，kw接收的是一个dict









