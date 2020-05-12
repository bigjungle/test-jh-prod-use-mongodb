import { Moment } from 'moment';

export interface IRegnCancSdmSuffix {
  id?: string;
  name?: string;
  descString?: string;
  orgInfo?: string;
  cancellationWay?: string;
  cancellationReason?: string;
  cancellationTime?: Moment;
  cancellationProof?: string;
  remarks?: string;
  ownerByLastName?: string;
  ownerById?: string;
}

export class RegnCancSdmSuffix implements IRegnCancSdmSuffix {
  constructor(
    public id?: string,
    public name?: string,
    public descString?: string,
    public orgInfo?: string,
    public cancellationWay?: string,
    public cancellationReason?: string,
    public cancellationTime?: Moment,
    public cancellationProof?: string,
    public remarks?: string,
    public ownerByLastName?: string,
    public ownerById?: string
  ) {}
}
