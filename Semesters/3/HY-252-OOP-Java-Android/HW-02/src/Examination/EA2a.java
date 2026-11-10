package Examination;

class Boat
{
    private String name;
    private double length;

    public Boat (String name, double length)
    { setName(name); setLength(length); }

    public void setName(String name)
    { this.name = name; }
    public String getName()
    { return this.name;}

    public void setLength(double length)
    { this.length = length;}
    public double getLength()
    { return this.length; }

    public String toString()
    { return (getName() + " μήκους " + getLength()); }
}

class Motorboat extends Boat
{
    private int numberOfSeats, horsePower;

    public Motorboat (String name, double length, int numberOfSeats, int horsePower)
    { super(name, length); setNumberOfSeats(numberOfSeats); setHorsePower(horsePower); }

    public void setNumberOfSeats(int numberOfSeats)
    { this.numberOfSeats = numberOfSeats; }
    public int getNumberOfSeats()
    { return this.numberOfSeats; }

    public void setHorsePower(int horsePower)
    { this.horsePower = horsePower; }
    public int getHorsePower()
    { return this.horsePower; }

    @Override public String toString()
    { return (super.toString() + " με " + getNumberOfSeats() + " θέσεις και μηχανή με ιπποδύναμη " + getHorsePower() + "."); }
}

public class EA2a // class Tester ...
{
    public static void main(String[] args)
    {
        Boat[] array = new Boat[10];

        for (int i = 0; i < array.length; i++)
        {
            if (i % 3 == 0) array[i] = new Motorboat("Ταχύπλοο-" + i, 10 + i, (int)(1 + (i % 4) + 1), 50 + i);
            else array[i] = new Boat("Σκάφος-" + i, 10 + i);
        }

        int totalSeats = 0;
        int totalHorsePower = 0;
        for (int i = 0; i < array.length; i++)
        {
            System.out.println(array[i].toString());
            if (array[i] instanceof Motorboat)
            {
                totalSeats += ((Motorboat) array[i]).getNumberOfSeats();
                totalHorsePower += ((Motorboat) array[i]).getHorsePower();
            }
        }

        System.out.println("ΤΑΧΥΠΛΟΑ\nΣύνολο θέσεων     : " + (double)totalSeats + "\nΣύνολο ιπποδύναμης: " + (double)totalHorsePower);
    }
}

