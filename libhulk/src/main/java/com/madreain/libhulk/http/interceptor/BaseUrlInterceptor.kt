package com.madreain.libhulk.http.interceptor

import android.text.TextUtils
import com.madreain.libhulk.config.HulkConfig
import com.madreain.libhulk.config.HulkConfig.IDENTIFICATION_PATH_SIZE
import okhttp3.*
import java.util.*

/**
 * @author madreain
 * @date 2020/4/16.
 * module：
 * description：捕获添加的Headers，然后修改baseURL
 */
open class BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(processRequest(chain.request()))
    }

    //添加
    open fun addHeaders(): Headers {
        return Headers.Builder()
            .build()
    }

    private fun processRequest(request: Request): Request {
        if (request == null) return request
        val newBuilder = request.newBuilder()
        //设置传递过来的相关的headers
        newBuilder.headers(addHeaders())
        //多bseurl的逻辑
        val domainName = obtainDomainNameFromHeaders(request)
        var httpUrl: HttpUrl? = null
        if (domainName != null) {
            httpUrl = HulkConfig.getMDomainNameHub()?.get(domainName)
            //设置多baseurl的另外的baseurl,将当前的domainName进行存储，便于
        } else {
            httpUrl = HulkConfig.getMDomainNameHub()?.get(HulkConfig.getDomainName())
        }

        if (null != httpUrl) {
            val newUrl: HttpUrl = parseUrl(httpUrl, request.url)
            return newBuilder
                .url(newUrl)
                .build()
        }
        return newBuilder.build()
    }

    private fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl {
        if (null == domainUrl) return url
        val builder = url.newBuilder()
        val pathSize: Int = resolvePathSize(url, builder)
        for (i in 0 until url.pathSize) { //当删除了上一个 index, PathSegment 的 item 会自动前进一位, 所以 remove(0) 就好
            builder.removePathSegment(0)
        }
        val newPathSegments: MutableList<String> =
            ArrayList()
        newPathSegments.addAll(domainUrl.encodedPathSegments)
        if (url.pathSize > pathSize) {
            val encodedPathSegments =
                url.encodedPathSegments
            for (i in pathSize until encodedPathSegments.size) {
                newPathSegments.add(encodedPathSegments[i])
            }
        } else require(url.pathSize >= pathSize) {
            String.format(
                "Your final path is %s, the pathSize = %d, but the #baseurl_path_size = %d, #baseurl_path_size must be less than or equal to pathSize of the final path",
                url.scheme + "://" + url.host + url.encodedPath, url.pathSize, pathSize
            )
        }
        for (PathSegment in newPathSegments) {
            builder.addEncodedPathSegment(PathSegment)
        }

        val httpUrl = builder
            .scheme(domainUrl.scheme)
            .host(domainUrl.host)
            .port(domainUrl.port)
            .build()
        return httpUrl
    }


    private fun resolvePathSize(httpUrl: HttpUrl, builder: HttpUrl.Builder): Int {
        val fragment = httpUrl.fragment
        var pathSize = 0
        val newFragment = StringBuffer()
        if (fragment?.indexOf("#") == -1) {
            val split = fragment.split("=").toTypedArray()
            if (split.size > 1) {
                pathSize = split[1].toInt()
            }
        } else {
            if (fragment?.indexOf(IDENTIFICATION_PATH_SIZE) == -1) {
                val index = fragment.indexOf("#")
                newFragment.append(fragment.substring(index + 1, fragment.length))
                val split =
                    fragment.substring(0, index).split("=").toTypedArray()
                if (split.size > 1) {
                    pathSize = split[1].toInt()
                }
            } else {
                fragment?.let {
                    val split: Array<String> =
                        it.split(IDENTIFICATION_PATH_SIZE).toTypedArray()
                    newFragment.append(split[0])
                    if (split.size > 1) {
                        val index = split[1].indexOf("#")
                        if (index != -1) {
                            newFragment.append(split[1].substring(index, split[1].length))
                            val substring = split[1].substring(0, index)
                            if (!TextUtils.isEmpty(substring)) {
                                pathSize = substring.toInt()
                            }
                        } else {
                            pathSize = split[1].toInt()
                        }
                    }
                }
            }
        }
        if (TextUtils.isEmpty(newFragment.toString())) {
            builder.fragment(null)
        } else {
            builder.fragment(newFragment.toString())
        }
        return pathSize
    }

    private fun obtainDomainNameFromHeaders(request: Request): String? {
        val headers =
            request.headers(HulkConfig.getDomainName())
        if (headers == null || headers.size == 0) return null
        require(headers.size <= 1) { "Only one " + HulkConfig.getDomainName() + " in the headers" }
        return request.header(HulkConfig.getDomainName())
    }


}