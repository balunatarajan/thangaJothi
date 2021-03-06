

import {Injectable} from '@angular/core';
import {Count} from '../../comps/other/Count';
import {TranInfo} from '../../comps/transaction/tran-info';
import {TranSearch} from '../../comps/transaction/tran-search';
import {TranCode} from '../../comps/trancode/tran-code';

import {Http,Response,RequestOptions,RequestMethod,Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
        import 'rxjs/add/operator/map';
//import 'rxjx/add/operator/catch';

@Injectable()
export class TransactionService {

    getData:string;
    private serverIp:string ='blrlw4258';
    private serverPort:string='4040';
    private url : string = 'http://'+this.serverIp+':'+this.serverPort;

    private getTransactionUrl: string = this.url+ '/rest/tjgs/tran/all';
    private getTransactionByPageUrl: string = this.url+ '/rest/tjgs/tran/allbypage';
    private postAddTransactionUrl: string = this.url+ '/rest/tjgs/tran/add';
    private putAddTransactionUrl: string = this.url+ '/rest/tjgs/tran/modify';
    private getCountUrl: string = this.url+'/rest/tjgs/getCount'; 

    private postAddTranCodeUrl: string = this.url+ '/rest/tjgs/tranhead/add';
    private putTranCodeUrl: string = this.url+ '/rest/tjgs/tranhead/modify';
    private getTranCodeUrl: string = this.url+ '/rest/tjgs/tranhead/all';
    private getTransactionByCrit:string = this.url+ '/rest/tjgs/tran/criteria';
    private reportHUrl: string = this.url+ '/rest/tjgs/tran/report/headerwise';
    private reportMUrl: string = this.url+ '/rest/tjgs/tran/report/monthwise';

constructor(private http : Http){}

public getAllTransByCriteria(tranSearch:TranSearch) :Observable<TranInfo[]>{
        var search = JSON.stringify(tranSearch);
        var url = this.getTransactionByCrit+'?tcLike='+ tranSearch.tcLike 
                                        +'&fDate='+ tranSearch.fDate 
                                        +'&tDate='+ tranSearch.tDate 
                                        +'&descLike='+tranSearch.descLike
                                        +'&voucherLike='+tranSearch.voucherLike;
      
        return this.http.get(url)
                    .map(this.extractTrans);
}

public getAllTransForYear(yearOf:number) :Observable<TranInfo[]>{
      
        return this.http.get(this.reportMUrl+'?year='+ yearOf)
                    .map(this.extractTrans);
}

public getAllTransForYearHeaderWise(yearOf:number) :Observable<TranInfo[]>{
        return this.http.get(this.reportHUrl+'?year='+ yearOf)
                    .map(this.extractTrans);
}


public  getTransCountInDb() :Observable<Count> {
        //getCount?table=anbar
        console.log('TransactionService::getTransCountInDb Enter');
        return this.http.get(this.getCountUrl+'?table=trans')
                  .map(this.extractCount);
}

extractCount(resp: Response){
        console.log('TransactionService::extractCount Enter');
        let body = resp.json();
        return body;
}

public addNewTransaction(ti:TranInfo){
      
            console.log('TransactionService::addNewTransaction Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddTransactionUrl, tranToPost, options).
                                                map((res:any) => res.json());
}

public getTransactionByPage(pageSize : number,pageNo : number) :Observable<TranInfo[]> {
        console.log('TransactionService::getTransactionByPage Enter');
        console.log('URL ' + this.getTransactionByPageUrl+'?page='+pageNo+'&size='+pageSize);
        return this.http.get(this.getTransactionByPageUrl+'?page='+pageNo+'&size='+pageSize)
                    .map(this.extractTrans);
}

//utility method
extractTrans(resp: Response){        
        console.log('TransactionService::getTransactionByPage Enter');
        let body = resp.json();
        console.log('Transaction DATA>'+JSON.stringify(body));
        return body || [] ;  // if body is null return empty array []
}

public modifyTransaction(ti:TranInfo) {
           console.log('TransactionService::modifyTransaction Enter');
           // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            return  this.http.put(this.putAddTransactionUrl, tranToPost, options).
                                                map((res:any) => {
                                                
                                                res.json()
                                                });                                                
 }

///////// TRAN CODE ///////////////////////////////

public addNewTranCode(ti:TranCode){
           console.log('TransactionService::addNewTranCode Enter');
      
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddTranCodeUrl, tranToPost, options).
                                                map((res:any) => res.json());
}


public modifyTranCode(ti:TranCode){
            console.log('TransactionService::modifyTranCode Enter');
      
             // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post transcation : before service call : '+tranToPost);
            return  this.http.put(this.putTranCodeUrl, tranToPost, options).
                                                map((res:any) => res.json());                                                
 }


public getAllTranCode() :Observable<TranCode[]> {
        
        console.log('TransactionService::getAllTranCode Enter');
      
        return this.http.get(this.getTranCodeUrl)
                    .map(this.extractTranCode);
}

//utility method
extractTranCode(resp: Response){        
        console.log('TransactionService::extractTranCode Enter');
      
        let body = resp.json();
        console.log('TRAN Code>'+JSON.stringify(body));
        return body || [] ;  // if body is null return empty array []
}

public  getTransCodeInDb() :Observable<Count> {
        console.log('TransactionService::getTransCodeInDb Enter');
        console.log('Inside getAnbarCountInDb .. '+this.getCountUrl+'?table=tcode');
        return this.http.get(this.getCountUrl+'?table=tcode')
                  .map(this.extractTCCount);
}

extractTCCount(resp: Response){
        let body = resp.json();
        return body;
}

}