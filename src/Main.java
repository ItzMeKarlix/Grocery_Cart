import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public static boolean isLoggedIn = false;
    static User user = new User();
    static JFrame section_frame = new JFrame();
    static JFrame showItem_frame, cart_frame;
    static JPanel sel_panel_item, panel_cart;
    static Cart cart = new Cart();

    static Section FruitsAndVegetables = new Section("Fruits and Vegetables", new ArrayList<Item>() {{
        add(new Item("Apple", 20.50, 1, "1 piece"));
        add(new Item("Banana", 8, 1, "1 piece"));
        add(new Item("Tomato", 150.75, 1, "1 kilo"));
        add(new Item("Potato", 90, 0.50, "½ kilo"));
        add(new Item("Strawberry", 206.50, 0.25, "¼ kilo"));
    }});

    static Section FrozenMeats = new Section("Frozen Meats", new ArrayList<Item>() {{
        add(new Item("Fish", 250.75, 1, "kilo"));
        add(new Item("Pork Meat", 170, 1, "kilo"));
        add(new Item("Chicken", 180.50, 1, "kilo"));
        add(new Item("Beef Meat", 500.25, 1, "kilo"));
    }});

    static Section CannedJarredGoods = new Section("Canned / JarredGoods", new ArrayList<Item>() {{
        add(new Item("555 Tuna", 25.25, 1, "piece"));
        add(new Item("Aregentina Corned Beef", 30.75, 1, "piece"));
        add(new Item("Maling", 53, 1, "piece"));
        add(new Item("Silver Swan", 15.75, 1, "piece"));
        add(new Item("UFC Ketchup", 11.50, 1, "piece"));
        add(new Item("Wow Ulam", 23, 1, "piece"));
        add(new Item("Spam", 109.50, 1, "piece"));
        add(new Item("San Marino", 30.75, 1, "piece"));
    }});
    static Section DairyAndBakingGoods = new Section("Dairy and Baking Goods", new ArrayList<Item>() {{
        add(new Item("Butter", 35.25, 1, "piece"));
        add(new Item("Star Margarine", 30.75, 1, "piece"));
        add(new Item("Flour", 40, 1, "kilo"));
    }});
    static Section Beverage = new Section("Beverage", new ArrayList<Item>() {{
        add(new Item("Gin", 42.75, 1, "piece"));
        add(new Item("Zest-o", 12.00, 1, "piece"));
        add(new Item("Vita milk ", 35.00, 1, "piece"));
        add(new Item("Cobra", 24.50, 1, "piece"));
        add(new Item("Bearbrand Sterilized", 38.25, 1, "piece"));
        add(new Item("Red horse", 110.00, 1, "piece"));
    }});
    static Section PersonalCareAndCleaners = new Section("Personal Care And Cleaners", new ArrayList<Item>() {{
        add(new Item("Tissue", 11.50, 1, "piece"));
        add(new Item("Joy", 10, 1, "piece"));
        add(new Item("Safeguard", 19.75, 1, "piece"));
        add(new Item("Mr. clean (bar)", 27.25, 1, "piece"));
        add(new Item("Downey", 15, 1, "piece"));
        add(new Item("Tende care (powder)", 21, 1, "piece"));
        add(new Item("Modess", 47.25, 1, "pack (8pcs)"));
        add(new Item("Gatsby", 50.75, 1, "piece"));
    }});

    public static void main(String[] args) {
        SectionsPane();
//        Sections();
        MainMenu(); // una
    }

    static void MainMenu() {
        isLoggedIn = false;
        while (!isLoggedIn) {
            log("LOG" + isLoggedIn);
            if (!isLoggedIn) {
                int mainmenuChoice = CustomButtons("Welcome to shop", "Shop", new Object[]{"Login", "Register"});
                log("MainMenu choice:" + mainmenuChoice);
                switch (mainmenuChoice) {
                    case 0: {
                        isLoggedIn = Login();
                        break;
                    }
                    case 1: {
                        isLoggedIn = Register();
                        break;
                    }
                    default: {
                        JOptionPane.showMessageDialog(null, "Thank You");
                        SystemExit();
                    }
                }
                if (isLoggedIn) {
                    Sections();
                    log("Login: " + isLoggedIn);
                }
            }
        }
    }

    // return true if login sucess
    static boolean Login() {
        log("Login START");
        String usernameTxt, passTxt;

        JTextField usernameTextField = new JTextField();
        JPasswordField passTextField = new JPasswordField();
        passTextField.setEchoChar('*');

        JComponent[] components = new JComponent[]{
                new JLabel("Username: "),
                usernameTextField,
                new JLabel("Password:"),
                passTextField,
        };

        while (true) {
            int res = JOptionPane.showConfirmDialog(null, components, "Login", JOptionPane.OK_CANCEL_OPTION); // show login jpane
            log("Login res = " + res);
            if (res == JOptionPane.CANCEL_OPTION) return false;
            if (res != 0) SystemExit();

            usernameTxt = usernameTextField.getText();
            passTxt = passTextField.getText();
            if (usernameTxt.isEmpty() || passTxt.isEmpty()) {
                ErrorMessage("All fields must be filled", "Login Failed");
                continue;
            }
            if (!user.checkifUserExists(usernameTxt)) {
                ErrorMessage("User does not exist", "Login Failed");
                continue;
            }
            if (!user.checkPass(usernameTxt, passTxt)) {
                log(user.checkPass(usernameTxt, passTxt));
                ErrorMessage("Password Incorrect", "Login Failed");
                continue;
            }
            JOptionPane.showMessageDialog(null, "Login Success", "Login", JOptionPane.INFORMATION_MESSAGE);
            return true;
            // Sections();
        }
    }


    // return true if account created and login sucess
    static boolean Register() {
        log("REGISTER START");
        String usernameTxt, pass1Txt, pass2Txt, fullNameTxt, contactTxt;

        JTextField usernameTextField = new JTextField();
        JPasswordField pass1TextField = new JPasswordField();
        pass1TextField.setEchoChar('*');
        JPasswordField pass2TextField = new JPasswordField();
        pass2TextField.setEchoChar('*');
        JTextField fullNameTextField = new JTextField();
        JTextField contactTextField = new JTextField();

        JComponent[] components = new JComponent[]{
                new JLabel("Username: "),
                usernameTextField,
                new JLabel("Password (min 8 char): "),
                pass1TextField,
                new JLabel("Confirm Password:"),
                pass2TextField,
                new JLabel("Full Name:"),
                fullNameTextField,
                new JLabel("Contact:"),
                contactTextField
        };
        while (true) {
            String err = "";
            int res = JOptionPane.showConfirmDialog(null, components, "REGISTER", JOptionPane.OK_CANCEL_OPTION); // show jpane

            log(res);
            if (res == 2) return false;
            if (res != 0) SystemExit();

            usernameTxt = usernameTextField.getText();
            pass1Txt = pass1TextField.getText();
            pass2Txt = pass2TextField.getText();
            fullNameTxt = fullNameTextField.getText();
            contactTxt = contactTextField.getText();

            if (usernameTxt.isEmpty() || pass1Txt.isEmpty() || pass2Txt.isEmpty() || fullNameTxt.isEmpty()
                    || contactTxt.isEmpty()) {
                ErrorMessage("All fields must be filled", "Registeration Failed");
                continue;
            }
            if (user.checkifUserExists(usernameTxt)) err += "User Exists.\n";
            if (!pass1Txt.equals(pass2Txt)) err += "Passwords do not match.\n";
            if (pass1Txt.length() < 8) err += "Password Must Be 8 or more.\n";
            try {
                Integer.parseInt(contactTxt);
            } catch (Exception e) {
                err += "Contact must be a number.\n";
            }
            if (!err.isEmpty()) {
                ErrorMessage(err, "Registration Failed");
            } else {
                JOptionPane.showMessageDialog(null, "Username: " + usernameTxt + "\nPassword: " + pass1Txt + "\nName: " + fullNameTxt + "\nContact: " + contactTxt);
                user.UserRegistration(usernameTxt, pass1Txt, fullNameTxt, Integer.parseInt(contactTxt));
                JOptionPane.showMessageDialog(null, "Registered Successfully", "Register", JOptionPane.INFORMATION_MESSAGE);
                return Login();
            }
        }
    }


    //-----------------------------------------------------------------------
    static void Sections() {
        section_frame.setVisible(true);
    }

//-----------------------------------------------------------------------

    static void SectionsPane() {

        section_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        section_frame.setTitle("Grocery Sections");
        //        showItem_frame.setResizable(false);
        section_frame.setSize(500, 550);
        section_frame.setLocationRelativeTo(null);
        section_frame.setLayout(new GridLayout(0, 1, 0, 5));

        //Panel
        JPanel sel_panel_section = new JPanel();
        sel_panel_section.setPreferredSize(new Dimension(150, 150));

        //Label
        JLabel label1 = new JLabel("Grocery Item Sections");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 15));

        section_frame.add(label1);

        //Selection Buttons
        JButton btn1 = new JButton("Fruits & Vegetable");
        JButton btn2 = new JButton("Frozen Meats");
        JButton btn3 = new JButton("Canned/Jarred Goods");
        JButton btn4 = new JButton("Dairy And Baking Goods");
        JButton btn5 = new JButton("Beverage");
        JButton btn6 = new JButton("Personal Care");

        section_frame.add(btn1);
        section_frame.add(btn2);
        section_frame.add(btn3);
        section_frame.add(btn4);
        section_frame.add(btn5);
        section_frame.add(btn6);

        // btn actions
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(FruitsAndVegetables);
            }
        });
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(FrozenMeats);
            }
        });
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(CannedJarredGoods);
            }
        });
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(DairyAndBakingGoods);
            }
        });
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(Beverage);
            }
        });
        btn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                section_frame.dispose();
                ItemsPane(PersonalCareAndCleaners);
            }
        });


        //Check Out Selection
        JButton btn_cancel = new JButton("Cancel");
        JButton btn_cart = new JButton("Cart");
        JButton btn_checkout = new JButton("Check Out");

        btn_cart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.getCart().size() > 0) {
                    section_frame.dispose();
                    CartPane();
                } else {
                    JOptionPane.showMessageDialog(null, "Cart is empty");
                }
            }
        });
        btn_checkout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.getCart().size() > 0) {
                    section_frame.dispose();
                    CheckOutPane();
                } else {
                    JOptionPane.showMessageDialog(null, "No Items");
                }
            }
        });

        btn_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = CustomButtons("Are you sure you want to cancel your purchases?", "Confirmation", new Object[]{"Yes", "No"});
                log("RES ASDASDDAS" + res);

                if (res == 0) {
//                    sel_panel_section.removeAll(); // Remove all items from the selection panel
//                    main_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
//                    main_panel.repaint(); // Repaint the panel to reflect changes
                    section_frame.dispose(); // Close the current frame
//                    checkout_frame.removeAll(); // Remove all items from the frame
                    MainMenu();
                }
            }
        });

        btn_checkout.setPreferredSize(new Dimension(150, 50));
        btn_cart.setPreferredSize(new Dimension(150, 50));
        btn_cancel.setPreferredSize(new Dimension(150, 50));

        sel_panel_section.add(btn_cancel);
        sel_panel_section.add(btn_checkout);
        sel_panel_section.add(btn_cart);
        section_frame.add(sel_panel_section);
    }

//-----------------------------------------------------------------------

    private static void ItemsPane(Section section) {
        showItem_frame = new JFrame();
        showItem_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showItem_frame.setTitle(section.getName());
        showItem_frame.setResizable(false);
        showItem_frame.setSize(500, 500);
        showItem_frame.setLocationRelativeTo(null);
        showItem_frame.setVisible(true);
        showItem_frame.setLayout(new GridLayout(0, 1, 0, 5));

        //Panel
        sel_panel_item = new JPanel();
        sel_panel_item.setPreferredSize(new Dimension(150, 150));

        //Label
        JLabel label1 = new JLabel(section.getName());
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        showItem_frame.add(label1);

        //item Buttons
        createButtons(section, showItem_frame);

        //Check Out Selection
        JButton btn_back = new JButton("Back");
        btn_back.setPreferredSize(new Dimension(150, 50));

        sel_panel_item.add(btn_back);
        showItem_frame.add(sel_panel_item);
        log(sel_panel_item);
        btn_back.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sel_panel_item.removeAll(); // Remove all items from the selection panel
                        sel_panel_item.revalidate(); // Revalidate the panel to ensure it updates correctly
                        sel_panel_item.repaint(); // Repaint the panel to reflect changes
                        showItem_frame.dispose(); // Close the current frame
                        showItem_frame.removeAll(); // Remove all items from the frame
                        Sections();
                    }
                });
    }

    //-----------------------------------------------------------------------
    private static void CheckOutPane() {

        // Initialization
        JLabel label_items;

        // Checkout Frame
        JFrame checkout_frame = new JFrame();
        checkout_frame.setTitle("Checkout");
        checkout_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkout_frame.setSize(500, 500);
        checkout_frame.setLocationRelativeTo(null);

        // Checkout Panels
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        JPanel label_panel = new JPanel();
        label_panel.setLayout(new BorderLayout());
        JPanel cart_panel = new JPanel();
        cart_panel.setLayout(new GridLayout(0, 1, 0, 4));

        // Top Label
        JLabel label1 = new JLabel("Review your Grocery Items");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        label_panel.add(label1);
        JLabel totalItems = new JLabel("Total Items: " + cart.getCart().size());
        JLabel totalPriceJLabel = new JLabel("TOTAL: " + cart.getTotalPrice());

        // Items
        HashMap<Item, Double> cartItems = cart.getCart();
        String[][] items = new String[cartItems.size()][4];
        int i = 0;
        for (Item item : cartItems.keySet()) {
            log("CheckoutPanel : " + item.getItemName() + " | " + item.getItemPrice() + " | " + cartItems.get(item) + " | " + (item.getItemPrice() * cartItems.get(item)));
            items[i][0] = item.getItemName();
            items[i][1] = item.getItemPrice() + "";
            items[i][2] = cartItems.get(item) + "";
            items[i][3] = (item.getItemPrice() * cartItems.get(item)) + "";
            i++;
        }

        String column[] = {"Item", "Price", "Quantity", "Total"};
        JTable table = new JTable(items, column);
        JScrollPane sp = new JScrollPane(table);
        cart_panel.add(sp);
        table.setDefaultEditor(Object.class, null);
        //Panel
        panel_cart = new JPanel();

        // Bottom Panel
        JPanel btns_panel = new JPanel();
        btns_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btns_panel.setPreferredSize(new Dimension(500, 70));

        // Buttons Panel
        JPanel buttons_panel = new JPanel();
        JButton btn_cancel = new JButton("Back");
        JButton btn_checkout = new JButton("Payment");

        btn_cancel.setPreferredSize(new Dimension(150, 50));
        btn_checkout.setPreferredSize(new Dimension(150, 50));

        // Receipt Totals Labels
        JPanel totals_panel = new JPanel();
        totals_panel.setLayout(new GridLayout(2, 2, 0, 5));

        JLabel totalQuantityLabel = new JLabel("Total Quantity: " + cart.getTotalQuantity());
        JLabel totalLabel = new JLabel("Total: " + cart.getTotalPrice());

        btns_panel.setVisible(true);
        totals_panel.setVisible(true);
        totals_panel.add(totalQuantityLabel);
        totals_panel.add(totalLabel);

        buttons_panel.add(btn_cancel);
        buttons_panel.add(btn_checkout);

        main_panel.add(label_panel, BorderLayout.NORTH);
        main_panel.add(cart_panel, BorderLayout.CENTER);
        main_panel.add(btns_panel, BorderLayout.SOUTH);

        btns_panel.add(totals_panel, BorderLayout.NORTH);
        btns_panel.add(buttons_panel, BorderLayout.SOUTH);

        checkout_frame.setVisible(true);
        checkout_frame.add(main_panel);

        btn_cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        main_panel.removeAll(); // Remove all items from the selection panel
                        main_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
                        main_panel.repaint(); // Repaint the panel to reflect changes
                        checkout_frame.dispose(); // Close the current frame
                        checkout_frame.removeAll(); // Remove all items from the frame
                        Sections();
                    }
                });
        btn_checkout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double cash;
                int res = CustomButtons("Payment Method: ", "Payment", new Object[]{"Cash", "Card"});
                log("CHECKOUT " + res);
                if (res == 0) {
                    while (true) {
                        try {
                            cash = CashPaymentPane();
                            log("Checkout: " + cash);
                            if (cash == -1) {
                                return;
                            }
                            if (cash < cart.getTotalPrice()) {
                                JOptionPane.showMessageDialog(null, "Insufficient Cash");
                            } else {
                                main_panel.removeAll(); // Remove all items from the selection panel
                                main_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
                                main_panel.repaint(); // Repaint the panel to reflect changes
                                checkout_frame.dispose(); // Close the current frame
                                checkout_frame.removeAll(); // Remove all items from the frame
                                ReceiptPane("Cash", cash);
                                break;
                            }
                        } catch (Exception exc) {
                            ErrorMessage("Enter ", "Cash Payment");
                        }
                    }
                } else if (res == 1) {
                    int cv;
                    while (true){
                        cv = CardPaymentPane();
                        log("CARD CV: " +cv);
                        if (cv > 0 ) {
                            JOptionPane.showMessageDialog(null, "Transaction Success");
                            main_panel.removeAll(); // Remove all items from the selection panel
                            main_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
                            main_panel.repaint(); // Repaint the panel to reflect changes
                            checkout_frame.dispose(); // Close the current frame
                            checkout_frame.removeAll(); // Remove all items from the frame
                            ReceiptPane("Card", 0);
                            break;
                        } else if (cv == -1){
                            break;
                        }
                    }
                }
            }
        });
    }

//-----------------------------------------------------------------------

    static void ReceiptPane(String method, double cash) {

        // Initialization
        JLabel label_items;

        // Checkout Frame
        JFrame receipt_frame = new JFrame();
        receipt_frame.setTitle("Receipt");
        receipt_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        receipt_frame.setSize(500, 500);
        receipt_frame.setLocationRelativeTo(null);


        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        // ITEMS
        HashMap<Item, Double> cartItems = cart.getCart();
        String[][] items = new String[cartItems.size()][4];
        log("CART SIZE: " + cartItems.size());
        int i = 0;
        for (Item item : cartItems.keySet()) {
            log("CART : " + item.getItemName() + " | " + item.getItemPrice() + " | " + cartItems.get(item) + " | " + (item.getItemPrice() * cartItems.get(item)));
            items[i][0] = item.getItemName();
            items[i][1] = item.getItemPrice() + " / " + item.getItemUnit();
            items[i][2] = cartItems.get(item) + "";
            items[i][3] = (item.getItemPrice() * cartItems.get(item)) + "";
            i++;
        }

        String column[] = {"Item name", "Price", "Quantity", "Total"};
        JTable table = new JTable(items, column);
        table.setDefaultEditor(Object.class, null);
        //            table.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(table);
        receipt_frame.add(sp);
        //        cart_frame.setSize(300,400);
        receipt_frame.setVisible(true);

        //Panel
        panel_cart = new JPanel();
        panel_cart.setLayout(new BorderLayout());
        //panel_cart.setPreferredSize(new Dimension(150,150));

        // Receipt Totals Frame
        JPanel btn_panel = new JPanel();
        btn_panel.setLayout(new GridLayout(0, 1, 0, 5));
        btn_panel.setPreferredSize(new Dimension(1, 100));

        // Receipt Totals Labels
        JLabel totalQuantityLabel = new JLabel("Total Quantity: " + cart.getTotalQuantity());
        JLabel totalLabel = new JLabel("Total: " + cart.getTotalPrice());
        JLabel cashLabel, changeLabel;

        btn_panel.setVisible(true);
        btn_panel.add(totalQuantityLabel);
        btn_panel.add(totalLabel);
        log("RECEIPT: " + cash + " | " + method);
        if (method.equals("Cash")) {
            btn_panel.add(new JLabel("Cash: " + cash));
            btn_panel.add(new JLabel("Change: " + (cash - cart.getTotalPrice())));
        } else if (method.equals("Card")) {
            btn_panel.add(new JLabel("Payed with Credit Card"));
        }
        receipt_frame.add(panel_cart, BorderLayout.NORTH);
        receipt_frame.add(btn_panel, BorderLayout.SOUTH);

        int res = CustomButtons("   Thank You For Your Purchace,\nWould you like to transact new purchace?", "Thank You", new Object[]{"New Purchase", "Exit"});
        if (res == 0) {
            cart.clearCart();
            btn_panel.removeAll(); // Remove all items from the selection panel
            btn_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
            btn_panel.repaint(); // Repaint the panel to reflect changes
            receipt_frame.dispose(); // Close the current frame
            receipt_frame.removeAll(); // Remove all items from the frame
            Sections();
        } else if (res == 1) {
            cart.clearCart();
            btn_panel.removeAll(); // Remove all items from the selection panel
            btn_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
            btn_panel.repaint(); // Repaint the panel to reflect changes
            receipt_frame.dispose(); // Close the current frame
            receipt_frame.removeAll(); // Remove all items from the frame
            MainMenu();
        } else {
            SystemExit();
        }
        main_panel.removeAll(); // Remove all items from the selection panel
        main_panel.revalidate(); // Revalidate the panel to ensure it updates correctly
        main_panel.repaint(); // Repaint the panel to reflect changes
        receipt_frame.dispose(); // Close the current frame
        receipt_frame.removeAll(); // Remove all items from the frame
    }

//-----------------------------------------------------------------------

    // method for frame amount and input
    static void getAmountFrame(Item currentItem) {
        JTextField amountTextField = new JTextField();
        JComponent[] components = new JComponent[]{
                new JLabel("Item: " + currentItem.getItemName()),
                new JLabel("Price: " + currentItem.getItemPrice()),
                new JLabel("Stock: " + currentItem.getItemStock()),
                new JLabel("Quantity: "),
                amountTextField,
        };
        int res = JOptionPane.showConfirmDialog(null, components, "Enter Item Quantity", JOptionPane.OK_CANCEL_OPTION);
        if (res != 0) return;
        String amountText = amountTextField.getText();
        if (amountText.isEmpty()) {
            ErrorMessage("Quantity must be filled", "Quantity");
            return;
        }
        try {
            double amount = Double.parseDouble(amountText);
            if (currentItem.getItemStock() >= amount) {
                if (amount < 0 || amount > currentItem.getItemStock()+1) {
                    ErrorMessage("Quantity must be greater than 0 and less than or equal the stock", "Quantity");
                } else {
                    cart.addToCart(currentItem, amount); // add to cart
                    currentItem.SubtractStock(amount);
                    JOptionPane.showMessageDialog(null, "Added to Cart", "Cart", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                ErrorMessage("Entered Amount Cannot be greater than stock.", "Amount greater than stock.");
            }
        } catch (Exception e) {
            ErrorMessage("Amount must be digits.", "Amount Error");
        }
    }
//-----------------------------------------------------------------------

    static double CashPaymentPane() {
        double cash;
        JTextField amountTextField = new JTextField();
        JComponent[] components = new JComponent[]{
                new JLabel("Total Price: " + cart.getTotalPrice()),
                new JLabel("Enter Cash Amount: "),
                amountTextField
        };
        int res = JOptionPane.showConfirmDialog(null, components, "Cash Payment: ", JOptionPane.OK_CANCEL_OPTION);
        if (res != 0) return -1;
        String amountText = amountTextField.getText();
        if (amountText.isEmpty()) {
            ErrorMessage("Please Enter cash amount.", "Cash Payment");
        }
        try {
            double amount = Double.parseDouble(amountText);
            if (amount < 0) ErrorMessage("Cash must be greater than 0", "Cash Payment");
            return amount;
        } catch (Exception e) {
            ErrorMessage("Invalid Input", "Cash Payment");
            return -1;
        }
    }

    //-----------------------------------------------------------------------
    static int CardPaymentPane() {
        JTextField amountTextField = new JTextField();
        JComponent[] components = new JComponent[]{
                new JLabel("PIN"),
                amountTextField,
        };
        int res = JOptionPane.showConfirmDialog(null, components, "Card PIN: ", JOptionPane.OK_CANCEL_OPTION);
        log("CardPaymentPane Res: " + res);
        if (res != 0)
        { log("RETURN 1 " + res);return -1;

        }
        log("Exit 1");
        String amountText = amountTextField.getText();
        if (amountText.isEmpty()) {
            log("RETURN 2 " +res);
            ErrorMessage("Please Enter CV number.", "Card Payment");
            return 0;
        }
        if (amountText.length() != 4) {
            log("Exit 3 " + res);
            ErrorMessage("CV number must be 4 digits.", "Card Payment");
            return 0;
        }
        try {
            log("Exit 4 " + res);
            int amount = Integer.parseInt(amountText);
            if (amount < 0) ErrorMessage("Please Enter Card Pin", "Card Payment");
            return amount;
        } catch (Exception e) {
            log("Exit 5 " + res);
            ErrorMessage("Invalid Input", "Card Payment");
            return -1;
        }
    }

//-----------------------------------------------------------------------

    // cart
    private static void CartPane() {
        cart_frame = new JFrame();
        cart_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart_frame.setTitle("Your Cart");
        cart_frame.setResizable(false);
        cart_frame.setSize(500, 500);
        cart_frame.setLocationRelativeTo(null);
        cart_frame.setVisible(true);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        // ITEMS
        HashMap<Item, Double> cartItems = cart.getCart();

        String[][] items = new String[cartItems.size()][4];
        log("CART SIZE: " + cartItems.size());
        int i = 0;
        for (Item item : cartItems.keySet()) {
            log("CART : " + item.getItemName() + " | " + item.getItemPrice() + " | " + cartItems.get(item) + " | " + (item.getItemPrice() * cartItems.get(item)));
            items[i][0] = item.getItemName();
            items[i][1] = item.getItemPrice() + " / " + item.getItemUnit();
            items[i][2] = cartItems.get(item) + "";
            items[i][3] = (item.getItemPrice() * cartItems.get(item)) + "";
            i++;
        }

        String column[] = {"Item name", "Price", "Quantity", "Total"};
        JTable table = new JTable(items, column);
        table.setDefaultEditor(Object.class, null);

//            table.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(table);
        cart_frame.add(sp);
        //        cart_frame.setSize(300,400);
        cart_frame.setVisible(true);

        //Panel
        panel_cart = new JPanel();
        panel_cart.setLayout(new BorderLayout());
        //        panel_cart.setPreferredSize(new Dimension(150,150));

        //Check Out Selection
        JPanel btn_panel = new JPanel();
        btn_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_panel.setPreferredSize(new Dimension(150, 60));

        JButton btn_back = new JButton("Back");
        JButton btn_removeItem = new JButton("Remove Item");
        JButton btn_checkOut = new JButton("Check Out");
        btn_back.setPreferredSize(new Dimension(140, 50));
        btn_checkOut.setPreferredSize(new Dimension(140, 50));
        btn_removeItem.setPreferredSize(new Dimension(140, 50));

        btn_panel.add(btn_removeItem);
        btn_panel.add(btn_back);
        btn_panel.add(btn_checkOut);

        btn_panel.setVisible(true);
        cart_frame.add(panel_cart, BorderLayout.NORTH);
        cart_frame.add(btn_panel, BorderLayout.SOUTH);


        btn_removeItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cart.getCart().size() == 0) {
                            ErrorMessage("Cart is empty", "Cart");
                            return;
                        }
                        removeItem(cart_frame);
                    }
                }
        );

        btn_back.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        panel_cart.removeAll(); // Remove all items from the selection panel
                        panel_cart.revalidate(); // Revalidate the panel to ensure it updates correctly
                        panel_cart.repaint(); // Repaint the panel to reflect changes
                        cart_frame.dispose(); // Close the current frame
                        cart_frame.removeAll(); // Remove all items from the frame
                        Sections();
                    }
                });

        btn_checkOut.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        panel_cart.removeAll(); // Remove all items from the selection panel
                        panel_cart.revalidate(); // Revalidate the panel to ensure it updates correctly
                        panel_cart.repaint(); // Repaint the panel to reflect changes
                        cart_frame.dispose(); // Close the current frame
                        cart_frame.removeAll(); // Remove all items from the frame
                        CheckOutPane();
                    }
                });
    }
//-----------------------------------------------------------------------

    static void createButtons(Section section, JFrame frame) {
        for (Item item : section.getItems()) {
            final Item currentItem = item;

            JButton btn = new JButton(currentItem.getItemName() + " | ₱" + currentItem.getItemPrice() +
                    " / " + item.getItemUnit());
            btn.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            getAmountFrame(currentItem);
                        }
                    });
            frame.add(btn);
        }
    }

    // ----------------------------------------------------------------------
    static void removeItem(JFrame cartFrame) {
        JFrame frameRemove = new JFrame();
        frameRemove.setAlwaysOnTop(true);

        String[] options = new String[cart.getCart().size()];

        int i = 0;
        for (Item item : cart.getCart().keySet()) {
            log("remove Item: " + item.getItemName());
            options[i] = item.getItemName();
            i++;
        }

        //...and passing `frame` instead of `null` as first parameter
        Object selectionObject = JOptionPane.showInputDialog(frameRemove, "Choose Item to Subtract", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        String selectionString = selectionObject.toString();
        for (Item item : cart.getCart().keySet()) {
            if ((item.getItemName()).equals(selectionString)) {
                String stringSub = JOptionPane.showInputDialog("Item: "+item.getItemName()+ "\nIn Cart: " + cart.getCart().get(item) + "\nSubtract Amount: ");
                if (stringSub == null) {
                    ErrorMessage("No amount entered","Return Item");
                    return;
                }
                try {
                    double sub = Double.parseDouble(stringSub);
                    cart.subtractItemQuan(item, sub);
                    item.ReturnItem(sub);
                    JOptionPane.showMessageDialog(null, "Item Subtracted / Removed", "Cart", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e){
                    ErrorMessage("Please dnter digits only.","Input Mismatch");
                }
                    if (cart.getCart().size() != 0) {
                        CartPane();
                    } else {
                        Sections();
                    }
                cartFrame.dispose(); // Close the current frame
                cartFrame.removeAll(); // Remove all items from the frame
                break;
            }
        }
    }

    private static int CustomButtons(Object message, String title, Object[] options) {
        return JOptionPane.showOptionDialog(
                null, // Parent component (null means center on screen)
                message, // Message to display
                title, // Dialog title
                JOptionPane.OK_CANCEL_OPTION, // Option type (Yes, No, Cancel)
                JOptionPane.QUESTION_MESSAGE, // Message type (question icon)
                null, // Custom icon (null means no custom icon)
                options, // Custom options array
                options[0] // Initial selection
        );
    }

    private static void log(Object log) {
        System.out.println("CLASS MAIN: " + log);
    }

    private static void SystemExit() {
        System.exit(0);
    }

    static void ErrorMessage(Object err, String title) {
        JOptionPane.showMessageDialog(null, err, title, JOptionPane.ERROR_MESSAGE);
    }
}