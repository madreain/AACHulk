package com.madreain.libhulk.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

/**
 * @author madreain
 * @date 2019-07-04.
 * module： Shell相关工具类
 * description：
 */
class ShellUtils private constructor() {
    /**
     * 返回的命令结果
     */
    class CommandResult(
        /**
         * 结果码
         */
        var result: Int,
        /**
         * 成功信息
         */
        var successMsg: String?,
        /**
         * 错误信息
         */
        var errorMsg: String?
    )

    companion object {
        /**
         * 是否是在root下执行命令
         *
         * @param command 命令
         * @param isRoot  是否需要root权限执行
         * @return CommandResult
         */
        fun execCmd(command: String, isRoot: Boolean): CommandResult {
            return execCmd(arrayOf(command), isRoot, true)
        }

        /**
         * 是否是在root下执行命令
         *
         * @param commands 多条命令链表
         * @param isRoot   是否需要root权限执行
         * @return CommandResult
         */
        fun execCmd(
            commands: List<String>,
            isRoot: Boolean
        ): CommandResult {
            return execCmd(
                commands, isRoot, true
            )
        }

        /**
         * 是否是在root下执行命令
         *
         * @param command         命令
         * @param isRoot          是否需要root权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        fun execCmd(
            command: String,
            isRoot: Boolean,
            isNeedResultMsg: Boolean
        ): CommandResult {
            return execCmd(
                arrayOf(command),
                isRoot,
                isNeedResultMsg
            )
        }

        /**
         * 是否是在root下执行命令
         *
         * @param commands        命令链表
         * @param isRoot          是否需要root权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        fun execCmd(
            commands: List<String?>?,
            isRoot: Boolean,
            isNeedResultMsg: Boolean
        ): CommandResult {
            return execCmd(
                commands, isRoot, isNeedResultMsg
            )
        }
        /**
         * 是否是在root下执行命令
         *
         * @param commands        命令数组
         * @param isRoot          是否需要root权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        /**
         * 是否是在root下执行命令
         *
         * @param commands 多条命令数组
         * @param isRoot   是否需要root权限执行
         * @return CommandResult
         */
        @JvmOverloads
        fun execCmd(
            commands: Array<String>?,
            isRoot: Boolean,
            isNeedResultMsg: Boolean = true
        ): CommandResult {
            var result = -1
            if (commands == null || commands.size == 0) {
                return CommandResult(result, null, null)
            }
            var process: Process? = null
            var successResult: BufferedReader? = null
            var errorResult: BufferedReader? = null
            var successMsg: StringBuilder? = null
            var errorMsg: StringBuilder? = null
            var os: DataOutputStream? = null
            try {
                process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh")
                os = DataOutputStream(process.outputStream)
                for (command in commands) {
                    if (command == null) continue
                    os.write(command.toByteArray())
                    os.writeBytes("\n")
                    os.flush()
                }
                os.writeBytes("exit\n")
                os.flush()
                result = process.waitFor()
                if (isNeedResultMsg) {
                    successMsg = StringBuilder()
                    errorMsg = StringBuilder()
                    successResult = BufferedReader(
                        InputStreamReader(
                            process.inputStream,
                            "UTF-8"
                        )
                    )
                    errorResult = BufferedReader(
                        InputStreamReader(
                            process.errorStream,
                            "UTF-8"
                        )
                    )
                    var s: String?
                    while (successResult.readLine().also { s = it } != null) {
                        successMsg.append(s)
                    }
                    while (errorResult.readLine().also { s = it } != null) {
                        errorMsg.append(s)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                CloseUtils.Companion.closeIO(os, successResult, errorResult)
                process?.destroy()
            }
            return CommandResult(
                result,
                successMsg?.toString(),
                errorMsg?.toString()
            )
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}