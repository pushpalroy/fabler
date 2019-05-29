package com.techradge.fabler.utils;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


public class EmailValidatorTest {
    @Test
    public void emailValidator_ValidEmailSimple_ReturnsTrue() {
        assertThat(CommonUtils.isEmailValid("name@email.com")).isTrue();
    }

    @Test
    public void emailValidator_ValidEmailSubDomain_ReturnsTrue() {
        assertThat(CommonUtils.isEmailValid("name@email.co.in")).isTrue();
    }

    @Test
    public void emailValidator_InvalidEmailNoDomain_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("name@email")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailTwoAtSymbols_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("name@em@ail.com")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailSpecialCharacter_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("n^me@email.com")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailTwoDots_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("name@email..com")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailNoUserName_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("@email.com")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailEmptyString_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid("")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailNullValue_ReturnsFalse() {
        assertThat(CommonUtils.isEmailValid(null)).isFalse();
    }
}
