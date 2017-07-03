

import {Injectable} from '@angular/core';
import {Count} from '../../comps/other/Count';
//import {TranInfo} from '../../comps/Book/tran-info';
import {BookTran} from '../../comps/books/booktran';
import {MasterDataC} from '../../comps/other/masterData';

import {Http,Response,RequestOptions,RequestMethod,Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
        import 'rxjs/add/operator/map';
//import 'rxjx/add/operator/catch';

@Injectable()
export class BookService {

    getData:string;
    private serverIp:string ='blrlw4258';
    private serverPort:string='4040';
    private url : string = 'http://'+this.serverIp+':'+this.serverPort;

    private getTransactionUrl: string = this.url+ '/rest/tjgs/booktran/all';
    private getBookByPageUrl: string = this.url+ '/rest/tjgs/booktran/allbypage';
    private postAddBookUrl: string = this.url+ '/rest/tjgs/booktran/add';
    private putAddBookUrl: string = this.url+ '/rest/tjgs/booktran/modify';
    private getCountUrl: string = this.url+'/rest/tjgs/getCount'; 
    private getBookUrl: string = this.url+ '/rest/tjgs/book/all';

constructor(private http : Http){}

public  getAllBookNames() :Observable<MasterDataC[]> {
        //getCount?table=anbar
        console.log('BookService::getAllBookNames Enter');
        return this.http.get(this.getBookUrl)
                  .map(this.extractJson);
}

public  getBookTransCountInDb() :Observable<Count> {
        //getCount?table=anbar
        console.log('BookService::getTransCountInDb Enter');
        return this.http.get(this.getCountUrl+'?table=trans')
                  .map(this.extractJson);
}
public addNewBook(ti:BookTran ){
      
            console.log('BookService::addNewBook Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddBookUrl, tranToPost, options).
                                                map((res:any) => res.json());
}

public getBookByPage(pageSize : number,pageNo : number) :Observable<BookTran[]> {
        console.log('BookService::getBookByPage Enter');
        console.log('URL ' + this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize);
        return this.http.get(this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize)
                    .map(this.extractJson);
}

public modifyBook(ti:BookTran) {
           console.log('BookService::modifyBook Enter');
           // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            return  this.http.put(this.putAddBookUrl, tranToPost, options).
                                                map((res:any) => {res.json()});                                                
}

extractJson(resp: Response){
        console.log('BookService::extractCount Enter');
        let body = resp.json();
        return body;
}
}