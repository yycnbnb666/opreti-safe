package com.ai.safe.agent.util.stream

/**
 * 测试用扩展函数：将字符串转换为字符流
 */
fun String.asCharStream(): Stream<Char> {
    return this.asSequence().asStream()
} 