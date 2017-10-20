# 第三十五題 - 循環質數

197, 971 和 719 為循環質數

1. 利用Sieve of Eratosthenes方法列出質數真值表
2. 用迴圈檢查每一個質數,若為質數,則用字串循環的方法,檢查質數表是否為質數

```=java
private void CircularString() {
	String s = Integer.toString(357);
	for (int i = 0; i < s.length(); i++) {
		System.out.println(s.substring(i) + s.substring(0, i));
	}
}
​```