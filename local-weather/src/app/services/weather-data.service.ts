import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

@Injectable()
export class WeatherDataService{

    constructor(private http: HttpClient){}
    private options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };

    getLocationFromIp(ipKey): Observable<any> {
        let ipUrl = 'http://api.ipstack.com/check';
        return this.http.get(ipUrl + '?access_key=' + ipKey) //default format on response is JSON
            .catch(this.handleError)
    }

    getWeatherFromGeoId(weatherKey, id): Observable<any>{
        let weatherUrl = 'http://worksample-api.herokuapp.com/weather';
        return this.http.get(weatherUrl + '?id=' + `${id}` + '&key='+ weatherKey)
            .catch(this.handleError)
    }

    private handleError(errorResponse: HttpErrorResponse){
        if(errorResponse.error instanceof ErrorEvent){
            console.error('Client Side error: ', errorResponse.error.message, errorResponse.status);
        } else {
            console.error('Server Side error: ', errorResponse.error.message, errorResponse.status);
        }
        return new ErrorObservable('There is a problem with the service.')
    }

}