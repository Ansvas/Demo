package demo;

import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author anshul
 */
public class Transaction extends javax.swing.JFrame {
   
    
    String totalpoint;
    String transaction_amount;
    String userid;
    String validity;
    
    public Transaction(String mobno) {
    initComponents();
    System.out.println("Mob no is"+ mobno);
    
    userid = findinguserid(mobno);
    
    System.out.println("User id is"+ userid);
    
    validity=getconfig();
    
    totalpoint =find_active_point(userid);
    //set totalpoint value in textfield;
    Points.setText(totalpoint);
    }

    private Transaction() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        amount_field = new javax.swing.JTextField();
        Redeem = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Points = new javax.swing.JLabel();
        dont_redeem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Transaction");

        jLabel2.setText("Enter Transaction Amount");

        amount_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amount_fieldActionPerformed(evt);
            }
        });

        Redeem.setText("Redeem");
        Redeem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedeemActionPerformed(evt);
            }
        });

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        jLabel3.setText("Points Available");

        dont_redeem.setText("Don't Redeem");
        dont_redeem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dont_redeemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Back, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dont_redeem)))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(amount_field, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Redeem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(Points, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Points, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amount_field, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Back)
                    .addComponent(Redeem)
                    .addComponent(dont_redeem))
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private String getconfig()
    {
        try {
            Connection c = DBClass.getConnection();
            
            final PreparedStatement ps = c.prepareStatement("SELECT time_of_activation from config");
            final ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String value = resultSet.getString(1);
         //   c.close();
            return value;        
        } catch (Exception ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String find_active_point(String userid)
    {
        //set flag of all inactive point to zero
        
    /*    try {
                Connection c = DBClass.getConnection();
                final PreparedStatement ps = c.prepareStatement("UPDATE Points_granted SET flag=? WHERE WHERE Date < now()-interval ? month");
                ps.setString(1, "0");
                ps.setString(2, validity);
                ps.executeUpdate();
            } catch (Exception ex) {
                Logger.getLogger(Acknowlegement.class.getName()).log(Level.SEVERE, null, ex);
            } */
        
    try {
        //retrive sum of relivent points
        
        Connection c = DBClass.getConnection();
        
        final PreparedStatement ps = c.prepareStatement("SELECT sum(points) FROM Points_granted WHERE Userid =? and flag=? and Date >= now()-interval ? month ORDER BY Date ASC");
        ps.setString(1, userid);
        ps.setString(2, "1");
        ps.setString(3, validity);
        final ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        String value = resultSet.getString(1);
        //c.close();
        return value;
                                      
                                
                                
    } catch (Exception ex) {
        Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
        
    }
     
    public static String findinguserid(String mobno)
    {
    try {
        Connection c = DBClass.getConnection();
        
        PreparedStatement ps = c.prepareStatement("SELECT * from User WHERE Mobno = ?");
        ps.setString(1, mobno);
        ResultSet resultSet = ps.executeQuery();

        if(resultSet.next()){
         String userid1 = resultSet.getString(1);
         System.out.println("userid in method"+ userid1);
     //    c.close();
         return userid1;
        }
    } catch (Exception ex) {
        Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
        
    }
    
    
    
    private void amount_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amount_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amount_fieldActionPerformed

    private void RedeemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedeemActionPerformed

        if(amount_field.getText().length()==0)  // Checking for empty field
      JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields"); 
        
        transaction_amount = amount_field.getText();
        
        String points_used = Acknowlegement.find_points_used(totalpoint,transaction_amount,userid);
        System.out.println("points used are"+points_used);
        String points_granted = "0";
        String points_remaining =Acknowlegement.points_remaining(points_granted,points_used,totalpoint);
        int ipoints_used=Integer.parseInt(points_used);
        int itran_amt=Integer.parseInt(transaction_amount);
        int ipayable=itran_amt-ipoints_used;
        String payable=Integer.toString(ipayable);
        
        //move to acknoledgment
         Acknowlegement tf2 =new Acknowlegement(points_used,points_granted,points_remaining,payable);
         tf2.setVisible(true);
         dispose();  

    }//GEN-LAST:event_RedeemActionPerformed

    private void dont_redeemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dont_redeemActionPerformed
    
     String trans_amt=amount_field.getText();
      System.out.println(trans_amt);
    String points_granted = Acknowlegement.points_granted(trans_amt,userid);
    Acknowlegement tf2 =new Acknowlegement("0",points_granted,totalpoint,trans_amt);
    System.out.println(points_granted);
    
         tf2.setVisible(true);
         dispose();
        
    }//GEN-LAST:event_dont_redeemActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        DemoStart tf2 =new DemoStart();
         tf2.setVisible(true);
         dispose();
    }//GEN-LAST:event_BackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JLabel Points;
    private javax.swing.JButton Redeem;
    private javax.swing.JTextField amount_field;
    private javax.swing.JButton dont_redeem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
