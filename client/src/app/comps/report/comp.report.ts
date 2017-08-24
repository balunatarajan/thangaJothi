import { Component } from '@angular/core';
import {TranInfo} from '../transaction/tran-info';
import {Count} from './../other/Count';
import {TransactionService} from '../../services/transaction/transaction.service'
import {TranCode} from '../trancode/tran-code';
import {TranSearch} from '../transaction/tran-search';
import * as jsPDF from 'jspdf'
import {DatePipe} from '@angular/common';
import {CurrencyPipe} from '@angular/common';
import {ReportCreator} from './report.creator';
//let jsPDF = require('jspdf');

@Component({
  selector: 'trans-list',
  templateUrl: '../../../tmpls/reports/report-tmpls.html',
  providers: [DatePipe,TransactionService,CurrencyPipe ] 
})

export class GenerateReport { 
        public yearEndM:number = 2014;
        public yearEndH:number = 2014;

        transList: Array<TranInfo>=[];
        
        constructor(private ts:TransactionService, private dp:DatePipe,private cp:CurrencyPipe) {
 
        }
 
        public getMonthlyReport(){
            this.ts.getAllTransForYear(this.yearEndM).subscribe(
            data => this.transList = data,
            err => console.log(err),
            () =>{
                    this.getJSPDFReportMonthwise();
            }  );

        }

        public getHeaderWiseReport(){
            this.ts.getAllTransForYearHeaderWise(this.yearEndH).subscribe(
            data => this.transList = data,
            err => console.log(err),
            () =>{
                    this.genJSPDFReportHeaderwise();
                });
        }

        getJSPDFReportMonthwise(){
            var doc = new jsPDF();
            var openBalance = this.transList[0].amount;
            this.transList.shift();  // removes the first element from array
            
            var report = new ReportCreator(this.dp,this.cp,openBalance,false,this.yearEndM);
            report.genHeader(doc);
            report.genTitle(doc,'MONTHLY INCOME EXPENSES FOR THE YEAR  '+(this.yearEndM-1)+'-'+this.yearEndM);
            report.populateDataMonthWise(doc,this.transList);
            report.genMonthlyConsolidatedReport(doc);

            doc.save('Test.pdf');
        }
        genJSPDFReportHeaderwise(){
            var doc = new jsPDF();
            var openBalance = this.transList[0].amount;
            this.transList.shift();  // removes the first element from array
           
            var report = new ReportCreator(this.dp,this.cp,openBalance,true,this.yearEndH);
            report.genHeader(doc);
            report.genTitle(doc,'HEADER WISE TRANSACTION FOR THE YEAR  '+(this.yearEndH-1)+'-'+this.yearEndH);   
          
            report.populateDataMonthWise(doc,this.transList);
            report.genMonthlyConsolidatedReport(doc);
            doc.save('Test.pdf');
        }

     

}
