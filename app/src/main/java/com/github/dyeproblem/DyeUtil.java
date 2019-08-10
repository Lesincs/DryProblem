package com.github.dyeproblem;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * author: cs丶
 * description:
 */
public class DyeUtil {

  public static SpannableString getColorSpanByViolence(String src, String keyWord, int color) {
    SpannableString spannableString = new SpannableString(src);

    out:for (int i = 0; i < src.length(); i++) {
      char charSrc = src.charAt(i);

      if (charSrc == keyWord.charAt(0)) {

        for (int j = 1; j < keyWord.length(); j++) {

          if (i+j>=src.length()||src.charAt(i + j) != keyWord.charAt(j)) {
            continue out;
          }
        }

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, i, i + keyWord.length(),
          Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        i += keyWord.length() - 1;
      }
    }

    return spannableString;
  }

  public static SpannableString getColorSpanByKMP(String src, String keyWord, int color) {
    SpannableString spannableString = new SpannableString(src);

    int[] next = getNext(keyWord);

    int i = 0, j = 0;

    while (i < src.length()){

      if (src.charAt(i) == keyWord.charAt(j)){
        i++;
        j++;
      }else {
        j = next[j];

        if (j == -1){
          j= 0;
          i++;
        }
      }

      if (j == keyWord.length()){
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, i - keyWord.length(), i,
          Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        j = 0;
      }
    }

    return spannableString;
  }


  /***
   * KMP算法用于获取next数组
   * @param keyWord
   * @return
   */
  private   static int[] getNext(String keyWord){
    char [] chars = keyWord.toCharArray();

    int[] prefixTable = new int[chars.length];

    int j = 1;

    while (j < chars.length -1){
      if (chars[j] == chars[prefixTable[j-1]]){
        prefixTable[j] = prefixTable[j-1]+1;
      }else {
        if (chars[j] == chars[0]){
          prefixTable[j] = 1;
        }else {
          prefixTable[j] = 0;
        }
      }

      j++;
    }

    for (int i = chars.length-1; i >=0 ; i--) {
      if (i == 0){
        prefixTable[i] = -1;
      }
      else {
        prefixTable[i]  = prefixTable[i-1];
      }
    }

    return prefixTable;
  }


}
