import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Spatialevent } from './spatialevent.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SpatialeventService {

    private resourceUrl = 'api/spatialevents';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(spatialevent: Spatialevent): Observable<Spatialevent> {
        const copy = this.convert(spatialevent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(spatialevent: Spatialevent): Observable<Spatialevent> {
        const copy = this.convert(spatialevent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Spatialevent> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date = this.dateUtils
            .convertDateTimeFromServer(entity.date);
    }

    private convert(spatialevent: Spatialevent): Spatialevent {
        const copy: Spatialevent = Object.assign({}, spatialevent);

        copy.date = this.dateUtils.toDate(spatialevent.date);
        return copy;
    }
}
