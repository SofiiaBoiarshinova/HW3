package org.hibernate_test;



import org.hibernate_test.data.entities.NodePack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Swing {
    int a3;
    public Swing(List<Tree> listTrees, List<TreeNode> listTreeNodes, Connection session){
        JFrame treelist = new JFrame("treelist");
        treelist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        List<JButton> jButtonList = new ArrayList<JButton>();
        int i = 1;
        String text ="";
        for (Tree tree:
                listTrees
             ) {
            text = "root " + i + ": " + tree.getRoot();
            JLabel label = new JLabel(text);
            jButtonList.add(new JButton(String.valueOf(tree.getRoot())));
            jButtonList.get(i-1).setBounds(150, 50 * i, 100, 25);
            jButtonList.get(i-1).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame (String.valueOf(tree.getRoot()));
                    JLabel root = new JLabel("root: " + tree.getRoot());
                    root.setBounds(200, 0, 100, 25);
                    frame.add(root);
                    frame.setSize(600, 900);
                    int j = 1;
                    for(int a : tree.getNodeList()){
                        for (TreeNode treeNode : listTreeNodes) {
                            if (treeNode.getId() == a) {
                                if (treeNode.isLeaf()) {
                                    JLabel node = new JLabel("id: " + String.valueOf(a) + " parent_id: " + treeNode.getParentNode());
                                    node.setBounds(50, 30 * j, 200, 25);
                                    frame.add(node);
                                    j++;
                                }
                                else {
                                    JLabel node = new JLabel("id: " + String.valueOf(a) + " parent_id: " + treeNode.getParentNode() + " children: " + treeNode.getChildrenList());
                                    node.setBounds(50, 30 * j, 500, 25);
                                    frame.add(node);
                                    j++;
                                }
                                JButton add = new JButton("Add child");
                                add.setBounds(350, 30 * (j-1), 100, 25);
                                add.addActionListener(new ActionListener() {
                                                          @Override
                                                          public void actionPerformed(ActionEvent e) {
                                                              JFrame addNodePack = new JFrame("Add child to " + a);
                                                              addNodePack.setLayout(null);
                                                              addNodePack.setSize(400, 200);
                                                              JTextField a1 = new JTextField();
                                                              a1.setBounds(80, 10, 180, 25);
                                                              JButton a2 = new JButton("Commit new child");
                                                              a2.setBounds(80, 45, 180, 25);
                                                              a2.addActionListener(new ActionListener() {
                                                                                       @Override
                                                                                       public void actionPerformed(ActionEvent e) {
                                                                                           a3 = Integer.parseInt(a1.getText());
                                                                                           addNodePack.setVisible(false);
                                                                                           NodePack nodePack = new NodePack(a3, a);
                                                                                           session.addNode(nodePack);
                                                                                       }
                                                                                       }
                                                              );
                                                              addNodePack.add(a1);
                                                              addNodePack.add(a2);
                                                              addNodePack.setVisible (true);

                                                          }
                                                      }
                                                      );
                                JButton delete = new JButton("Delete");
                                delete.setBounds(460, 30 * (j-1), 100, 25);
                                delete.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent e) {
                                                                 session.deleteNode(a);
                                                             }
                                                         }
                                );
                                frame.add(delete);
                                frame.add(add);
                                break;
                            }
                        }
                    }
                    JButton commit1 = new JButton("Save changes");
                    commit1.setBounds(300, 700, 150, 25);
                    commit1.addActionListener(new ActionListener() {
                                                 @Override
                                                 public void actionPerformed(ActionEvent e) {
                                                     session.commitChanges();
                                                 }
                                             }
                    );

                    frame.add(commit1);
                    frame.setLayout(null);
                    frame.setVisible (true);

                }
            });
            label.setBounds(50, 50 * i, 100, 25);
            treelist.add(label);
            treelist.add(jButtonList.get(i-1));

            i++;
        }
        JButton commit = new JButton("Save changes");
        commit.setBounds(300, 500, 150, 25);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                session.commitChanges();
            }
        }
        );

        treelist.add(commit);
        treelist.setSize(600, 600);
        treelist.setLayout(null);
        treelist.setVisible(true);
    }
}
