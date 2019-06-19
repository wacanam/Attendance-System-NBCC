/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Sitty Pangcatan
 */
public class TableStyle extends JTable {

    @Override
    public Component prepareRenderer(TableCellRenderer ren, int r, int c) {
        Component comp = super.prepareRenderer(ren, r, c);
        if (r % 2 == 0 && !isCellSelected(r, c)) {
            comp.setBackground(Color.white);
            comp.setForeground(Color.black);

        } else if (!isCellSelected(r, c)) {
            comp.setBackground(new Color(221, 221, 246));
            comp.setForeground(Color.black);

        } else {
            comp.setBackground(new Color(102, 0, 204));
            comp.setForeground(Color.white);
        }

        return comp;
    }

}
