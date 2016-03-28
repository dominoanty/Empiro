
package gui_pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

/**
 *
 * @author prashanth
 */
public class Howtoplay extends JFrame {

    public String htpinst[];
    public String imgloc[];
    public Howtoplay() {
        this.htpinst = new String[]{"<html>Select New Game to start a new game (duh).\n" +
                "<br>Enter a valid name tho</html>", "<html>5 citizens are in your kingdom by default, assign them tasks such as \n" +
                "<br>Food Production - Your kingdom needs food to survive, for trading.\n" +
                "<br>Wood Production- You need wood to house your citizens.</html>", "<html>Trade food or wood to get the gold you need gold to <br>create and train your army.</html>", "<html>Create your own army so,<br> you can wipe anyone who tries to wipe you.\n" +
                "<br>Or you can wipe anyone you want</html>", "<html>Increase the number of citizen that you have to add to work,<>br to increase the production rate.</html>", "<html>Shortly after you begin your game, you have to choose your sides,<br> Choose wisely.</html>"
            + "", "<html>Help allies!!</html>", "<html>AND BATTLE YOUR WAY TO GLORY!!!</html>"};
        this.imgloc= new String[]{Misc.imageLoc()+"openbook.jpg",
            Misc.imageLoc()+"build.jpg",
            Misc.imageLoc()+"gold.png",
            Misc.imageLoc()+"Trainciti.jpg",
            Misc.imageLoc()+"build.jpg",
            Misc.imageLoc()+"ally.jpg",
            Misc.imageLoc()+"war.jpg",
           Misc.imageLoc()+ "glory.jpg"};
        index=0;
        initComponents();
        jLabel1.setFont(new Font("Verdana", Font.BOLD, 12));
        jLabel1.setText("<html><div style=\"text-align: center;\">" + htpinst[index] + "</html>");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);


    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Previous");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Next");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 80, 560, 130));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/openbook.jpg"))); // NOI18N
        jLabel3.setIcon(new ImageIcon(imgloc[0]));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 290));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jButton2)
                        .addGap(83, 83, 83)
                        .addComponent(jButton3))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(index==0){
            return;
        }
        
        else{
            index--;
           /* jLabel3.setIcon(new ImageIcon(
                        getClass().getResource(imgloc[index])));*/
            jLabel3.setIcon(new ImageIcon(imgloc[index]));
            //jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            //jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(imgloc[index]))); // NOI18N
            //jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 290));
            jLabel1.setFont(new Font("Verdana", Font.BOLD, 12));
            if(index==5)
                jLabel1.setForeground(Color.black);
            else if(index==1||index==4)
                jLabel1.setForeground(Color.gray);
            else
                jLabel1.setForeground(Color.white);
            jLabel1.setText("<html><div style=\"text-align: center;\">" + htpinst[index] + "</html>");
            
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        if(index==htpinst.length-1){
            return;
        }
        else{
            
            //jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            //jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(imgloc[index]))); // NOI18N
            //jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 290));
            index++;
            System.err.println(index);
            /*jLabel3.setIcon(new ImageIcon(
                        getClass().getResource(imgloc[index])));*/
            jLabel3.setIcon(new ImageIcon(imgloc[index]));
            jLabel1.setFont(new Font("Verdana", Font.BOLD, 12));
            if(index==5)
                jLabel1.setForeground(Color.black);
            else if(index==1||index==4)
                jLabel1.setForeground(Color.gray);
            else if(index==2)
                jLabel1.setForeground(Color.BLACK);
            else
                jLabel1.setForeground(Color.white);
            jLabel1.setText("<html><font size='20' color='green'><div style=\"text-align: center;\">" + htpinst[index] + "</html>");


        }
    }
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Howtoplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Howtoplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Howtoplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Howtoplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Howtoplay().setVisible(true);
            }
        });
    }
    int index;
    
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
}
