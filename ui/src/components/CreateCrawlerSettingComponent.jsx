import React, {Component} from 'react';
import CrawlerService from "../services/CrawlerService";
import TermList from "./TermList";

class CreateCrawlerSettingComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            link: '',
            terms: [{term:""}],
            depth: '',
            maxPages: ''
        }
    }

    handleChange = (e) => {
        if (["term"].includes(e.target.name)) {
            let terms = [...this.state.terms]
            terms[e.target.dataset.id][e.target.name] = e.target.value;
        } else {
            this.setState({[e.target.name]: e.target.value})
        }
    }

    addNewRow = () => {
        this.setState((prevState) => ({
            terms: [...prevState.terms, {term: ""}],
        }));
    }

    deteteRow = (index) => {
        this.setState({
            terms: this.state.terms.filter((s, sindex) => index !== sindex),
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        let words = [];
        this.state.terms.map((val) =>{
            words.push(val.term);
        })
        let crawlerSetting = {
            link: this.state.link,
            terms:  words,
            depth: this.state.depth,
            maxPages: this.state.maxPages
        };
        let seed = crawlerSetting.link;
        seed = seed.replace(/[^a-zа-яё0-9\s]/gi, '');
        console.log(seed);
        CrawlerService.createCrawlerSettings(crawlerSetting).then(res => {
            this.props.history.push('/api/crawlers' + '/' + seed + "/" + "10");
        })
    }

    clickOnDelete(record) {
        this.setState({
            terms: this.state.terms.filter(r => r !== record)
        });
    }

    render() {
        let { terms } = this.state
        return (
            <div>
                <div className="content">
                    <form onSubmit={this.handleSubmit} onChange={this.handleChange} >
                        <div className="row" style={{ marginTop: 20 }}>
                            <div className="col-sm-1"></div>
                            <div className="col-sm-10">
                                <div className="card">
                                    <div className="card-header text-center">Add Crawler Setting</div>
                                    <div className="card-body">
                                        <div className="row">
                                            <div className="col-sm-4">
                                                <div className="form-group ">
                                                    <label className="required">URL</label>
                                                    <input type="link"  name="link" id="link" className="form-control" placeholder="Enter URL" />
                                                </div>
                                            </div>
                                            <div className="col-sm-4">
                                                <div className="form-group ">
                                                    <label className="required">Depth</label>
                                                    <input type="depth"  name="depth" id="depth" className="form-control" placeholder="Enter Depth" />
                                                </div>
                                            </div>
                                            <div className="col-sm-4">
                                                <div className="form-group ">
                                                    <label className="required">Max scanned pages</label>
                                                    <input type="maxPages"  name="maxPages" id="maxPages" className="form-control" placeholder="Enter number of scanned pages" />
                                                </div>
                                            </div>
                                        </div>
                                        <table className="table">
                                            <thead>
                                            <tr>
                                                <th className="required" >Term</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <TermList add={this.addNewRow} delete={this.clickOnDelete.bind(this)} terms={terms} />
                                            </tbody>

                                        </table>
                                    </div>
                                    <div className="card-footer text-center"> <button type="submit" className="btn btn-primary text-center">Submit</button></div>
                                </div>
                            </div>
                            <div className="col-sm-1"></div>
                        </div>
                    </form>
                </div>

            </div>
        );
    }
}

export default CreateCrawlerSettingComponent;