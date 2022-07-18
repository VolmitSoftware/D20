package art.arcane.d20;

import art.arcane.d20.roll.Roll;

public class Main {
    public static void main(String[] a)
    {
        System.out.println(Roll.decode("ADV d10-4").encode());
    }
}
