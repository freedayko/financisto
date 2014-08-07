/*
 * Copyright (c) 2012 Denis Solonenko.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package ru.orangesoftware.financisto.blotter;

import android.content.Context;
import android.widget.TextView;
import ru.orangesoftware.financisto.db.DatabaseAdapter;
import ru.orangesoftware.financisto.db.MyEntityManager;
import ru.orangesoftware.financisto.db.TransactionsTotalCalculator;
import ru.orangesoftware.financisto.filter.WhereFilter;
import ru.orangesoftware.financisto.model.Total;

public class BlotterTotalCalculationTask extends TotalCalculationTask {

	private final DatabaseAdapter db;
    private final MyEntityManager em;
	private final WhereFilter filter;

	public BlotterTotalCalculationTask(Context context, DatabaseAdapter db, MyEntityManager em, WhereFilter filter, TextView totalText) {
        super(context, totalText);
		this.db = db;
        this.em = em;
		this.filter = filter;
	}

    @Override
    public Total getTotalInHomeCurrency() {
        TransactionsTotalCalculator calculator = new TransactionsTotalCalculator(db, em, filter);
        return calculator.getBlotterBalanceInHomeCurrency();
    }

    @Override
    public Total[] getTotals() {
        TransactionsTotalCalculator calculator = new TransactionsTotalCalculator(db, em, filter);
        return calculator.getTransactionsBalance();
    }

}
