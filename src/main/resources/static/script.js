const API_URL = `${window.location.origin}/api`;

// Rendeiza um card de equipamento a partir do objeto retornado pela API
function criarCardEquipamento(eq){
    const id = eq.idEquipamento || eq.id_equipamento || eq.id || '';
    const modelo = eq.modelo || '—';
    const ano = eq.ano || '—';
    const geracao = eq.geracao || '—';
    const fabricante = (eq.fabricante && (eq.fabricante.nome || eq.fabricante.nome)) || '—';

    return `
    <div class="glass-card rounded-3xl overflow-hidden flex flex-col transition-all duration-300 group" data-id="${id}">
      <div class="h-48 overflow-hidden relative">
        <img class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110" src="/images/placeholder.jpg" alt="${modelo}" />
        <div class="absolute inset-0 bg-gradient-to-t from-surface-container-lowest to-transparent opacity-60"></div>
        <span class="absolute top-4 right-4 bg-primary/20 backdrop-blur-md text-primary px-3 py-1 rounded-full font-label-sm text-label-sm border border-primary/30">${geracao}</span>
      </div>
      <div class="p-8 flex flex-col flex-1">
        <h3 class="font-headline-lg text-headline-lg text-on-surface mb-4">${modelo}</h3>
        <div class="space-y-3 mb-8">
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-outline text-sm">calendar_today</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">Ano: <span class="text-on-surface font-bold">${ano}</span></span>
          </div>
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-outline text-sm">precision_manufacturing</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">Fabricante: <span class="text-on-surface font-bold text-primary">${fabricante}</span></span>
          </div>
        </div>
        <div class="mt-auto">
          <a class="flex items-center justify-center gap-2 w-full py-4 border border-outline-variant/30 rounded-xl text-on-surface hover:bg-white/10 hover:border-white/40 transition-all font-title-md group/btn" href="#" data-id="${id}">Ver Detalhes <span class="material-symbols-outlined text-sm group-hover/btn:translate-x-1 transition-transform">arrow_forward</span></a>
        </div>
      </div>
    </div>
    `;
}

async function carregarEquipamentos(){
    const container = document.getElementById('equipamentos-grid');
    if(!container) return;
    container.innerHTML = '<p class="carregando">Carregando...</p>';
    try{
        const resp = await fetch(`${API_URL}/equipamentos`);
        if(!resp.ok) throw new Error('Erro ao buscar equipamentos');
        const dados = await resp.json();
        if(!dados || dados.length === 0){
            container.innerHTML = '<p class="aviso">Nenhum equipamento encontrado.</p>';
            return;
        }
        container.innerHTML = dados.map(criarCardEquipamento).join('');
        bindDetalhes();
        // tentar carregar imagens associadas (se houver) e substituir placeholder
        dados.forEach(eq => attachImagemIfAny(eq));
    }catch(err){
        console.error(err);
        container.innerHTML = '<p class="erro">Falha ao carregar equipamentos.</p>';
    }
}

async function attachImagemIfAny(eq){
    const id = eq.idEquipamento || eq.id_equipamento || eq.id;
    if(!id) return;
    try{
        const resp = await fetch(`${API_URL}/imagens/equipamento/${id}`);
        if(!resp.ok) return;
        const imgs = await resp.json();
        if(!imgs || imgs.length === 0) return;
        const first = imgs[0];
        const card = document.querySelector(`.glass-card[data-id='${id}']`);
        if(card){
            const imgEl = card.querySelector('img');
            if(imgEl && first.url) imgEl.src = first.url;
        }
    }catch(e){ /* ignora */ }
}

// Filtrar por geração via endpoint
async function filtrarPorGeracao(){
    const input = document.getElementById('filtroGeracao');
    const q = input ? input.value.trim() : '';
    if(!q){ carregarEquipamentos(); return; }
    const container = document.getElementById('equipamentos-grid');
    container.innerHTML = '<p class="carregando">Filtrando...</p>';
    try{
        const resp = await fetch(`${API_URL}/equipamentos/geracao/${encodeURIComponent(q)}`);
        if(!resp.ok) throw new Error('Nenhum equipamento');
        const dados = await resp.json();
        if(!dados || dados.length === 0){ container.innerHTML = `<p class="aviso">Nenhum equipamento encontrado para "${q}".</p>`; return; }
        container.innerHTML = dados.map(criarCardEquipamento).join('');
        bindDetalhes();
        dados.forEach(eq => attachImagemIfAny(eq));
    }catch(err){ console.error(err); container.innerHTML = '<p class="erro">Erro ao filtrar.</p>'; }
}

// Modal dinâmico usando endpoints de detalhe
async function abrirModalDetalhes(equipamentoId){
    const overlayExists = document.getElementById('msx-modal-overlay');
    if(overlayExists) overlayExists.remove();
    const overlay = document.createElement('div'); overlay.id='msx-modal-overlay'; overlay.style.position='fixed'; overlay.style.inset='0'; overlay.style.background='rgba(2,6,23,0.6)'; overlay.style.display='flex'; overlay.style.alignItems='center'; overlay.style.justifyContent='center'; overlay.style.zIndex='2000';
    overlay.addEventListener('click',(e)=>{ if(e.target===overlay) overlay.remove(); });

    const modal = document.createElement('div'); modal.id='msx-modal'; modal.style.width='min(900px,96%)'; modal.style.maxHeight='86vh'; modal.style.overflow='auto'; modal.style.borderRadius='1rem'; modal.style.padding='1rem'; modal.style.background='linear-gradient(180deg, rgba(255,255,255,0.03), rgba(255,255,255,0.02))';

    const close = document.createElement('button'); close.className='close-btn'; close.innerHTML='✕'; close.style.float='right'; close.style.margin='0'; close.addEventListener('click',()=>overlay.remove());
    modal.appendChild(close);

    const title = document.createElement('h3'); title.id='msx-modal-title'; modal.appendChild(title);
    const body = document.createElement('div'); body.id='msx-modal-body'; modal.appendChild(body);

    overlay.appendChild(modal); document.body.appendChild(overlay);

    try{
        const [eqResp, curiosResp, imgsResp, fontesResp, docsResp] = await Promise.all([
            fetch(`${API_URL}/equipamentos/${equipamentoId}`),
            fetch(`${API_URL}/curiosidades/equipamento/${equipamentoId}`),
            fetch(`${API_URL}/imagens/equipamento/${equipamentoId}`),
            fetch(`${API_URL}/fontes/equipamento/${equipamentoId}`),
            fetch(`${API_URL}/documentos/equipamento/${equipamentoId}`)
        ]);

        if(!eqResp.ok) { body.innerHTML='<p class="erro">Detalhes não encontrados.</p>'; return; }
        const eq = await eqResp.json();
        const curios = curiosResp.ok ? await curiosResp.json() : [];
        const imgs = imgsResp.ok ? await imgsResp.json() : [];
        const fontes = fontesResp.ok ? await fontesResp.json() : [];
        const docs = docsResp.ok ? await docsResp.json() : [];

        title.textContent = eq.modelo || 'Detalhes';
        body.innerHTML = '';
        const meta = document.createElement('div'); meta.className='meta'; meta.innerHTML = `<strong>Ano:</strong> ${eq.ano || 'N/A'} &nbsp; • &nbsp; <strong>Geração:</strong> ${eq.geracao || 'N/A'} &nbsp; • &nbsp; <strong>Fabricante:</strong> ${(eq.fabricante && eq.fabricante.nome) || 'N/A'}`;
        body.appendChild(meta);

        if(imgs && imgs.length){
            const imgSec = document.createElement('div'); imgSec.innerHTML='<h4>Imagens</h4>';
            imgs.forEach(i=>{ const im = document.createElement('img'); im.src = i.url; im.alt = eq.modelo; im.style.maxWidth='260px'; im.style.width='100%'; im.style.borderRadius='8px'; im.style.margin='6px 0'; imgSec.appendChild(im); });
            body.appendChild(imgSec);
        }

        if(curios && curios.length){ const cs = document.createElement('div'); cs.innerHTML='<h4>Curiosidades</h4>'; const ul = document.createElement('ul'); curios.forEach(c=>{ const li=document.createElement('li'); li.textContent=c.descricao; ul.appendChild(li); }); cs.appendChild(ul); body.appendChild(cs); }
        if(docs && docs.length){ const ds = document.createElement('div'); ds.innerHTML='<h4>Documentos</h4>'; const ul = document.createElement('ul'); docs.forEach(d=>{ const li=document.createElement('li'); const a=document.createElement('a'); a.href=d.url; a.target='_blank'; a.rel='noopener'; a.textContent=d.titulo || d.url; li.appendChild(a); ul.appendChild(li); }); ds.appendChild(ul); body.appendChild(ds); }
        if(fontes && fontes.length){ const fs = document.createElement('div'); fs.innerHTML='<h4>Fontes</h4>'; const ul = document.createElement('ul'); fontes.forEach(f=>{ const li=document.createElement('li'); const a=document.createElement('a'); a.href=f.url_original; a.target='_blank'; a.rel='noopener'; a.textContent=f.nome_site || f.url_original; li.appendChild(a); ul.appendChild(li); }); fs.appendChild(ul); body.appendChild(fs); }

    }catch(err){ console.error(err); body.innerHTML='<p class="erro">Erro ao carregar detalhes.</p>'; }
}

function bindDetalhes(){
    // links dentro dos cards
    const anchors = Array.from(document.querySelectorAll('.glass-card a'));
    anchors.forEach(a=>{
        a.addEventListener('click',(e)=>{
            e.preventDefault(); e.stopPropagation();
            const id = a.getAttribute('data-id') || a.closest('.glass-card')?.getAttribute('data-id');
            if(id) abrirModalDetalhes(id);
        });
    });
    // também permitir clique em todo o cartão
    const cards = Array.from(document.querySelectorAll('.glass-card'));
    cards.forEach(card=>{
        card.addEventListener('click', ()=>{ const id = card.getAttribute('data-id'); if(id) abrirModalDetalhes(id); });
    });
}

// hook dos botões/inputs
document.addEventListener('DOMContentLoaded', ()=>{
    carregarEquipamentos();
    const input = document.getElementById('filtroGeracao');
    const btns = Array.from(document.querySelectorAll('button'));
    const btnFiltrar = btns.find(b=> b.textContent && b.textContent.toLowerCase().includes('filtrar'));
    const btnRecarregar = btns.find(b=> b.textContent && b.textContent.toLowerCase().includes('recarregar'));
    if(btnFiltrar) btnFiltrar.addEventListener('click', (e)=>{ e.preventDefault(); filtrarPorGeracao(); });
    if(input) input.addEventListener('keyup', (e)=>{ if(e.key==='Enter') filtrarPorGeracao(); });
    if(btnRecarregar) btnRecarregar.addEventListener('click', (e)=>{ e.preventDefault(); if(input) input.value=''; carregarEquipamentos(); });
});
