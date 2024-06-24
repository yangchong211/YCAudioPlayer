//
// Created by 杨充 on 2024/6/23.
//

#include <iostream>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;


//1.3.3.4 auto，自动推导变量的类型
void test1_3_3_4();


int main () {
    test1_3_3_4();
    return 0;
}

//1.3.3.4 auto，自动推导变量的类型
void test1_3_3_4() {
    //在C++中，auto是一个关键字，用于自动推导变量的类型。使用auto关键字可以让编译器根据变量的初始化表达式自动推断出变量的类型，从而简化代码并提高可读性。
    //
    //下面是auto关键字的基本用法：
    auto variable = value;
    //在上述示例中，我们使用auto关键字声明了一个变量variable，并将其初始化为value。编译器会根据value的类型自动推断出variable的类型。
    //auto关键字的使用有以下几个注意点：
    //自动类型推导：auto关键字会根据初始化表达式的类型自动推导变量的类型。这意味着变量的类型将根据初始化值的类型而变化。
    //初始化必须存在：使用auto关键字声明变量时，必须进行初始化。编译器需要初始化表达式来推断变量的类型。
    //类型推导规则：auto关键字使用的类型推导规则与模板类型推导规则类似。它会考虑初始化表达式的值和引用性质，从而确定变量的类型。
    //可读性和简洁性：使用auto关键字可以使代码更加简洁和可读，特别是在处理复杂的类型或使用迭代器等情况下。
    //需要注意的是，auto关键字并不是万能的，它并不适用于所有情况。在某些情况下，显式指定变量的类型可能更加清晰和明确。
}





