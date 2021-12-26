package com.mousavi.hashem.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class DateUtilKtTest{

    @Test
    fun `return empty if input is not in yyyy-mm-dd format`(){
        val input = "2020/12/25"
        val dateFormat = dateFormat(input)
        assertThat(dateFormat).isEmpty()
    }

    @Test
    fun `return empty if input is not numerical`(){
        val input = "yyyy-12-25"
        val dateFormat = dateFormat(input)
        assertThat(dateFormat).isEmpty()
    }

    @Test
    fun `return true if convert right`(){
        val input = "2021-12-25"
        val expected = "2021 Dec 25"
        val dateFormat = dateFormat(input)
        assertThat(dateFormat).isEqualTo(expected)
    }

    @Test
    fun `return empty if input in null`(){
        val input = null
        val dateFormat = dateFormat(input)
        assertThat(dateFormat).isEmpty()
    }

}