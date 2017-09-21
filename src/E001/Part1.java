package E001;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.processing.Processor;

public class Part1 {
	public static void N001_Multiples35() {
		int n = 1000;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}
		System.out.printf("resule %d\n", sum);
	}

	/*
	 * Each new term in the Fibonacci sequence is generated by adding the previous
	 * two terms. By starting with 1 and 2, the first 10 terms will be:\ 1, 2, 3, 5,
	 * 8, 13, 21, 34, 55, 89, ... By considering the terms in the Fibonacci sequence
	 * whose values do not exceed four million, find the sum of the even-valued
	 * terms.
	 * 
	 */
	public static void N002_EvenFib() {
		long x1 = 1, x2 = 2;
		long sum = 0;
		while (x2 < 4000000) {
			if (x2 % 2 == 0)
				sum += x2;
			long tmp = x2;
			x2 = x1 + x2;
			x1 = tmp;
			// System.out.println(x2);
		}
		System.out.printf("EvenFib %d\n", sum);
	}

	/*
	 * The prime factors of 13195 are 5, 7, 13 and 29. What is the largest prime
	 * factor of the number 600851475143 ?
	 * 
	 */
	private static ArrayList<Long> listPrimes(long n) {
		ArrayList<Long> primelist = new ArrayList<Long>();
        boolean[] isPrime = new boolean[(int) (n+1)];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int factor = 2; factor*factor <= n; factor++) {
            if (isPrime[factor]) {
                for (int j = factor; factor*j <= n; j++) {
                    isPrime[factor*j] = false;
                }
            }
        }

        int primes = 0;
        for (long i = n; i >= 1; i--) {
            if (isPrime[(int)i])
            	primelist.add(i);
        }
        return primelist;
	}
	
	public static void N004_LargePrimeFactor() {
		long number = 600851475143L;
		long upper = (long) Math.sqrt((double) number);

//		Function<Long, Boolean> isPrime = (x) -> {
//			if (x < 2)listPrimes
//				return false;
//			if (x == 2)
//				return true;
//			if (x % 2 == 0)
//				return false;
//			for (int i = 3; i * i <= x; i += 2)
//				if (x % i == 0)
//					return false;
//			return true;
//		};
		
		ArrayList<Long> primes = listPrimes(upper);
		primes.forEach(n -> {
			if(number % n == 0) {
				System.out.println("found:"+n);
				return;
			}
			
		});
	}
	
    static Boolean isPalindrome(long num)
    {
    	String str = String.valueOf(num);
    	char[] s = str.toCharArray();
        for (int i=0; i<s.length/2; ++i) {
            if (s[i] != s[s.length - 1 - i])
                return false;
        }
        return true;
    }
    
	public static void N003_3Palindrome() {
		int x = 999;
		int max = 0;
		for (int i=0;i<899;i++) {
			int t = x - i;
			for(int j=0;j<i;j++) {
				int u = x - j;
				if(isPalindrome(t*u)) {
					if(t*u > max) {
						System.out.printf("%d %d = %d\n", t, u, t*u);
						max = t * u;
					}
				}
			}
			
		}
	}
	
	public static void N005_SmallestMulti() {
		long c = 21;
		while (1==1)
		{
		    boolean all = true;
		    for (int i = 1; i < 21; i++)
		    {
		        all = true;
		        if ( c % i != 0 )
		        {
		            all = false;
		            break;
		        }
		    }
		    if ( all )
		    {
		        System.out.println( c );
		        System.exit(0);
		    }

		    c++;
		}
	}
	
	public static void N006_SumSquare() {
		long n = 100;
		long squaresum = (long)Math.pow(n*(n+1)/2, 2) - n*(n+1)*(2*n+1) / 6;
		System.out.println(squaresum);//25164150
	}
	
	public static void N007_10001stprime() {
		int inp = 10001;
		 ArrayList<Integer> arr = new ArrayList<Integer>();
		    arr.add(2);
		    arr.add(3);

		    int counter = 4;

		    while(arr.size() < inp) {
		        if(counter % 2 != 0 && counter%3 != 0) {
		            int temp = 4;
		            while(temp*temp <= counter) {
		                if(counter % temp == 0) {
		                	//System.out.println("counter:"+counter);
		                    break;	
		                }
		                temp ++;
		            }
		            //System.out.println("temp:"+temp+"counter:"+counter);
		            if(temp*temp > counter) {
		                arr.add(counter);
		            }
		        }
		        counter++;
		    }

		    System.out.println(arr.get(inp-1)); 
	}
	
	public static void N008_LargestProduct() {
		int n = 13;
		String nums  = "73167176531330624919225119674426574742355349194934" + 
				"96983520312774506326239578318016984801869478851843" + 
				"85861560789112949495459501737958331952853208805511" + 
				"12540698747158523863050715693290963295227443043557" + 
				"6689車6648950445244523161731856403098711121722383113" + 
				"62229893423380308135336276614282806444486645238749" + 
				"30358907296290491560440772390713810515859307960866" + 
				"70172427121883998797908792274921901699720888093776" + 
				"65727333001053367881220235421809751254540594752243" + 
				"52584907711670556013604839586446706324415722155397" + 
				"53697817977846174064955149290862569321978468622482" + 
				"83972241375657056057490261407972968652414535100474" + 
				"82166370484403199890008895243450658541227588666881" + 
				"16427171479924442928230863465674813919123162824586" + 
				"17866458359124566529476545682848912883142607690042" + 
				"24219022671055626321111109370544217506941658960408" + 
				"07198403850962455444362981230987879927244284909188" + 
				"84580156166097919133875499200524063689912560717606" + 
				"05886116467109405077541002256983155200055935729725" + 
				"71636269561882670428252483600823257530420752963450";
		
		char [] digits = nums.toCharArray();
		
		int l = 0;
		int m = 0;
		long result = 0;
		while( m < digits.length) {
			l = digits[m] == '0' ? 0 : l+1;
			if(l == 13) {
				long tmp = 1;
				for(int i=m;i>m-13;i--)
					tmp = tmp*Character.getNumericValue(digits[i]);
				if(tmp > result) result = tmp;
				l = 12;
			}
			m++;
		}
		System.out.println(result);
	}

	//find a+b+c = 1000 and a^2+b^2=c^2
	public static void N009_PythagoreanTriplet() {
		int n = 1000; //a+b+c
		for(int i=334;i<1000;i++) {
			int s = (n-i)/2;
			for(int j=s;j<(n-i);j++) {
				int c = 1000-(i+j);
				if(i*i == j*j + c*c) {
					System.out.printf("%d %d %d\n", i, j, 1000-(i+j));
					System.out.printf("%d\n", i*j*c);
				}
			}
		}
	}
	
	public static void N010_SumPrimes() {
		int n = 2000000;
		
		int counter = 4;
		long sum = 5;

	    while(counter < n) {
	        if(counter % 2 != 0 && counter%3 != 0) {
	            int temp = 4;
	            while(temp*temp <= counter) {
	                if(counter % temp == 0) {
	                    break;	
	                }
	                temp ++;
	            }
	            if(temp*temp > counter) {
	                sum+=counter;
	            }
	        }
	        counter++;
	    }
	    System.out.println(sum);
	}
	
	public static void N011_LargestProduct() {
		int[][] grid = new int[][]{
			{  8, 02, 22, 97, 38, 15, 00, 40, 00, 75, 04, 05, 07, 78, 52, 12, 50, 77, 91,  8 },
			{ 49, 49, 99, 40, 17, 81, 18, 57, 60, 87, 17, 40, 98, 43, 69, 48, 04, 56, 62, 00 },
			{ 81, 49, 31, 73, 55, 79, 14, 29, 93, 71, 40, 67, 53, 88, 30, 03, 49, 13, 36, 65 },
			{ 52, 70, 95, 23, 04, 60, 11, 42, 69, 24, 68, 56, 01, 32, 56, 71, 37, 02, 36, 91 },
			{ 22, 31, 16, 71, 51, 67, 63, 89, 41, 92, 36, 54, 22, 40, 40, 28, 66, 33, 13, 80 },
			{ 24, 47, 32, 60, 99, 03, 45, 02, 44, 75, 33, 53, 78, 36, 84, 20, 35, 17, 12, 50 },
			{ 32, 98, 81, 28, 64, 23, 67, 10, 26, 38, 40, 67, 59, 54, 70, 66, 18, 38, 64, 70 },
			{ 67, 26, 20, 68, 02, 62, 12, 20, 95, 63, 94, 39, 63,  8, 40, 91, 66, 49, 94, 21 },
			{ 24, 55, 58, 05, 66, 73, 99, 26, 97, 17, 78, 78, 96, 83, 14, 88, 34, 89, 63, 72 },
			{ 21, 36, 23,  9, 75, 00, 76, 44, 20, 45, 35, 14, 00, 61, 33, 97, 34, 31, 33, 95 },
			{ 78, 17, 53, 28, 22, 75, 31, 67, 15, 94, 03, 80, 04, 62, 16, 14,  9, 53, 56, 92 },
			{ 16, 39, 05, 42, 96, 35, 31, 47, 55, 58, 88, 24, 00, 17, 54, 24, 36, 29, 85, 57 },
			{ 86, 56, 00, 48, 35, 71, 89, 07, 05, 44, 44, 37, 44, 60, 21, 58, 51, 54, 17, 58 },
			{ 19, 80, 81, 68, 05, 94, 47, 69, 28, 73, 92, 13, 86, 52, 17, 77, 04, 89, 55, 40 },
			{ 04, 52,  8, 83, 97, 35, 99, 16, 07, 97, 57, 32, 16, 26, 26, 79, 33, 27, 98, 66 },
			{ 88, 36, 68, 87, 57, 62, 20, 72, 03, 46, 33, 67, 46, 55, 12, 32, 63, 93, 53, 69 },
			{ 04, 42, 16, 73, 38, 25, 39, 11, 24, 94, 72, 18,  8, 46, 29, 32, 40, 62, 76, 36 },
			{ 20, 69, 36, 41, 72, 30, 23, 88, 34, 62, 99, 69, 82, 67, 59, 85, 74, 04, 36, 16 },
			{ 20, 73, 35, 29, 78, 31, 90, 01, 74, 31, 49, 71, 48, 86, 81, 16, 23, 57, 05, 54 },
			{ 01, 70, 54, 71, 83, 51, 54, 69, 16, 92, 33, 48, 61, 43, 52, 01, 89, 19, 67, 48 }
		};
		long large = 0;
		for(int i=0; i<grid.length;i++) {
			for(int j=0; j<grid[i].length;j++) {
				//check |
				if(i+3 < grid.length) {
					long t = grid[i][j] * grid[i+1][j] * grid[i+2][j] * grid[i+3][j];
					large = t > large? t : large;
				}
				
				//check --
				if(j+3 < grid[i].length) {
					long t = grid[i][j] * grid[i][j+1] * grid[i][j+2] * grid[i][j+3];
					large = t > large? t : large;
				}
				//check /
				if(i+3 < grid.length && j-3 >= 0) {
					long t = grid[i][j] * grid[i+1][j-1] * grid[i+2][j-2] * grid[i+3][j-3];
					large = t > large? t : large;
				}
				//check \
				if(i+3 < grid.length && j+3 < grid[i].length) {
					long t = grid[i][j] * grid[i+1][j+1] * grid[i+2][j+2] * grid[i+3][j+3];
					large = t > large? t : large;
				}
			}
			System.out.println(large);
		}
	}
	public static void N012_DivisibleTriangularNumber() {
		int n = 500; //500 divisors....
		int c = 0;
		int triNo = 1, step = 2;
		
		Function<Integer, Integer> divisors = (x) -> {
			int cd = 0;
			for(int i = 1 ; i < Math.sqrt(x) ; ++i) {
				if( x%i == 0) {
			           cd++;
			           if(x/i!=i) cd++;
				}
			}
			return cd;
		};
		while(c < 500) {
			//System.out.println(triNo);
			//Get next TriNo
			triNo += step;
			++step;
			c = divisors.apply(triNo);
		}
		System.out.println(triNo);
	}
	
	public static void N013_LargestSum() {
		String nums = "37107287533902102798797998220837590246510135740250" + 
				"46376937677490009712648124896970078050417018260538" + 
				"74324986199524741059474233309513058123726617309629" + 
				"91942213363574161572522430563301811072406154908250" + 
				"23067588207539346171171980310421047513778063246676" + 
				"89261670696623633820136378418383684178734361726757" + 
				"28112879812849979408065481931592621691275889832738" + 
				"44274228917432520321923589422876796487670272189318" + 
				"47451445736001306439091167216856844588711603153276" + 
				"70386486105843025439939619828917593665686757934951" + 
				"62176457141856560629502157223196586755079324193331" + 
				"64906352462741904929101432445813822663347944758178" + 
				"92575867718337217661963751590579239728245598838407" + 
				"58203565325359399008402633568948830189458628227828" + 
				"80181199384826282014278194139940567587151170094390" + 
				"35398664372827112653829987240784473053190104293586" + 
				"86515506006295864861532075273371959191420517255829" + 
				"71693888707715466499115593487603532921714970056938" + 
				"54370070576826684624621495650076471787294438377604" + 
				"53282654108756828443191190634694037855217779295145" + 
				"36123272525000296071075082563815656710885258350721" + 
				"45876576172410976447339110607218265236877223636045" + 
				"17423706905851860660448207621209813287860733969412" + 
				"81142660418086830619328460811191061556940512689692" + 
				"51934325451728388641918047049293215058642563049483" + 
				"62467221648435076201727918039944693004732956340691" + 
				"15732444386908125794514089057706229429197107928209" + 
				"55037687525678773091862540744969844508330393682126" + 
				"18336384825330154686196124348767681297534375946515" + 
				"80386287592878490201521685554828717201219257766954" + 
				"78182833757993103614740356856449095527097864797581" + 
				"16726320100436897842553539920931837441497806860984" + 
				"48403098129077791799088218795327364475675590848030" + 
				"87086987551392711854517078544161852424320693150332" + 
				"59959406895756536782107074926966537676326235447210" + 
				"69793950679652694742597709739166693763042633987085" + 
				"41052684708299085211399427365734116182760315001271" + 
				"65378607361501080857009149939512557028198746004375" + 
				"35829035317434717326932123578154982629742552737307" + 
				"94953759765105305946966067683156574377167401875275" + 
				"88902802571733229619176668713819931811048770190271" + 
				"25267680276078003013678680992525463401061632866526" + 
				"36270218540497705585629946580636237993140746255962" + 
				"24074486908231174977792365466257246923322810917141" + 
				"91430288197103288597806669760892938638285025333403" + 
				"34413065578016127815921815005561868836468420090470" + 
				"23053081172816430487623791969842487255036638784583" + 
				"11487696932154902810424020138335124462181441773470" + 
				"63783299490636259666498587618221225225512486764533" + 
				"67720186971698544312419572409913959008952310058822" + 
				"95548255300263520781532296796249481641953868218774" + 
				"76085327132285723110424803456124867697064507995236" + 
				"37774242535411291684276865538926205024910326572967" + 
				"23701913275725675285653248258265463092207058596522" + 
				"29798860272258331913126375147341994889534765745501" + 
				"18495701454879288984856827726077713721403798879715" + 
				"38298203783031473527721580348144513491373226651381" + 
				"34829543829199918180278916522431027392251122869539" + 
				"40957953066405232632538044100059654939159879593635" + 
				"29746152185502371307642255121183693803580388584903" + 
				"41698116222072977186158236678424689157993532961922" + 
				"62467957194401269043877107275048102390895523597457" + 
				"23189706772547915061505504953922979530901129967519" + 
				"86188088225875314529584099251203829009407770775672" + 
				"11306739708304724483816533873502340845647058077308" + 
				"82959174767140363198008187129011875491310547126581" + 
				"97623331044818386269515456334926366572897563400500" + 
				"42846280183517070527831839425882145521227251250327" + 
				"55121603546981200581762165212827652751691296897789" + 
				"32238195734329339946437501907836945765883352399886" + 
				"75506164965184775180738168837861091527357929701337" + 
				"62177842752192623401942399639168044983993173312731" + 
				"32924185707147349566916674687634660915035914677504" + 
				"99518671430235219628894890102423325116913619626622" + 
				"73267460800591547471830798392868535206946944540724" + 
				"76841822524674417161514036427982273348055556214818" + 
				"97142617910342598647204516893989422179826088076852" + 
				"87783646182799346313767754307809363333018982642090" + 
				"10848802521674670883215120185883543223812876952786" + 
				"71329612474782464538636993009049310363619763878039" + 
				"62184073572399794223406235393808339651327408011116" + 
				"66627891981488087797941876876144230030984490851411" + 
				"60661826293682836764744779239180335110989069790714" + 
				"85786944089552990653640447425576083659976645795096" + 
				"66024396409905389607120198219976047599490197230297" + 
				"64913982680032973156037120041377903785566085089252" + 
				"16730939319872750275468906903707539413042652315011" + 
				"94809377245048795150954100921645863754710598436791" + 
				"78639167021187492431995700641917969777599028300699" + 
				"15368713711936614952811305876380278410754449733078" + 
				"40789923115535562561142322423255033685442488917353" + 
				"44889911501440648020369068063960672322193204149535" + 
				"41503128880339536053299340368006977710650566631954" + 
				"81234880673210146739058568557934581403627822703280" + 
				"82616570773948327592232845941706525094512325230608" + 
				"22918802058777319719839450180888072429661980811197" + 
				"77158542502016545090413245809786882778948721859617" + 
				"72107838435069186155435662884062257473692284509516" + 
				"20849603980134001723930671666823555245252804609722" + 
				"53503534226472524250874054075591789781264330331690";
		char [] digits = nums.toCharArray();
		
		BigInteger sum = new BigInteger("0");
		for(int i=0;i<digits.length;i=i+50) {
			sum = sum.add(new BigInteger(new String(digits, i, 50)));
		}
		System.out.println(sum.toString().substring(0, 10));
	}
	public static void main(String[] args) {
		//N002_EvenFib();N013_LargestSum
		//N003_LargePrimeFactor();
		//N003_3Palindrome();
		//N005_SmallestMulti();
		//N006_SumSquare();
		//N007_10001stprime();
		//N008_LargestProduct();char [] digits = nums.toCharArray();
		//N009_PythagoreanTriplet();
		//N010_SumPrimes();
		//N011_LargestProduct();
		//N012_DivisibleTriangularNumber();
		N013_LargestSum();
	}

}
