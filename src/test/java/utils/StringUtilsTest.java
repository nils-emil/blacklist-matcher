package utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void getEditDistanceBetweenWords_ReturnsLargeDistance_IfLargeDifference() {
        // Given
        String str1 = "Nils";
        String str2 = "Emil";

        // When
        int editDistanceBetweenWords = StringUtils
                .getEditDistanceBetweenWords(str1, str2, str1.length(), str2.length());

        // Then
        assertEquals(3, editDistanceBetweenWords);
    }

    @Test
    public void getEditDistanceBetweenWords_ReturnsDifferenceOfOne_IfOneCharacterIsReplaced() {
        // Given
        String str1 = "Emil";
        String str2 = "Enil";

        // When
        int editDistanceBetweenWords = StringUtils
                .getEditDistanceBetweenWords(str1, str2, str1.length(), str2.length());

        // Then
        assertEquals(1, editDistanceBetweenWords);
    }

    @Test
    public void getEditDistanceBetweenWords_ReturnsDifferenceOfOne_IfOneNameIsSimilarButOneCharacterShorter() {
        // Given
        String str1 = "Jeesus";
        String str2 = "Jeesu";

        // When
        int editDistanceBetweenWords = StringUtils
                .getEditDistanceBetweenWords(str1, str2, str1.length(), str2.length());

        // Then
        assertEquals(1, editDistanceBetweenWords);
    }


}